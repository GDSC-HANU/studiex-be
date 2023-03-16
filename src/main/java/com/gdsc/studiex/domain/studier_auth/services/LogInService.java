package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Gender;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierPrivacy;
import com.gdsc.studiex.domain.studier.models.Url;
import com.gdsc.studiex.domain.studier.repositories.StudierPrivacyRepository;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import com.gdsc.studiex.domain.studier_auth.config.SessionConfig;
import com.gdsc.studiex.domain.studier_auth.models.Account;
import com.gdsc.studiex.domain.studier_auth.models.Email;
import com.gdsc.studiex.domain.studier_auth.models.Session;
import com.gdsc.studiex.domain.studier_auth.repositories.AccountRepository;
import com.gdsc.studiex.domain.studier_auth.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Service
public class LogInService {
    private final AccountRepository accountRepository;
    private final StudierRepository studierRepository;
    private final SessionRepository sessionRepository;
    private final StudierPrivacyRepository studierPrivacyRepository;
    private final SessionConfig sessionConfig;

    public OutputLogIn logIn(String fbAccessToken) throws BusinessLogicException {
        RestTemplate restTemplate = new RestTemplate();
        FacebookUser facebookUser = restTemplate.getForObject("https://graph.facebook.com/me?fields=id,name,email,gender,birthday&access_token="
                + fbAccessToken, FacebookUser.class);
        if(facebookUser != null) {
            String facebookId = facebookUser.getId();
            Account account = accountRepository.findByFacebookId(facebookId);
            if(account == null) {
                String birthday = facebookUser.getBirthday();
                Studier studier = Studier.newStudierBuilder()
                        .name(facebookUser.getName())
                        .gender(Gender.of(facebookUser.getGender()))
                        .yob(birthday != null ? Integer.parseInt(birthday.substring(birthday.length()-4)) : null)
                        .avatar(new Url("http://graph.facebook.com/" + facebookId +
                             "/picture?type=square&access_token=" + fbAccessToken))
                        .build();
                account = Account.newAccountBuilder()
                                .studierId(studier.getStudierId())
                                .facebookId(facebookId)
                                .email(new Email(facebookUser.getEmail()))
                                .build();
                studierRepository.save(studier);
                studierPrivacyRepository.save(StudierPrivacy.defaultStudierPrivacy(studier.getStudierId()));
                accountRepository.save(account);
            }
            Session session = Session.createSession(account.getStudierId());
            sessionRepository.save(session);
            String accessToken = session.genToken(sessionConfig.getTokenSecret());
            return new OutputLogIn(accessToken, account.getStudierId());
        } else {
            throw new BusinessLogicException("Cannot login by this facebook", null);
        }
    }

    @NoArgsConstructor
    @Getter
    public static class FacebookUser {
        private String id;
        private String name;
        private String email;
        private String gender;
        private String birthday;
    }

    @AllArgsConstructor
    public static class OutputLogIn {
        private String accessToken;
        private Id studierId;
    }
}
