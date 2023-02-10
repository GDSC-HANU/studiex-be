package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.studier.models.Gender;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.Url;
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
    private final SessionConfig sessionConfig;

    public String logIn(String fbAccessToken) throws BusinessLogicException {
        RestTemplate restTemplate = new RestTemplate();
        FacebookUser facebookUser = restTemplate.getForObject("https://graph.facebook.com/me?fields=id,name,email,gender,birthday,link&access_token="
                + fbAccessToken, FacebookUser.class);
        if(facebookUser != null) {
            String facebookId = facebookUser.getId();
            Account account = accountRepository.findByFacebookId(facebookId);
            if(account == null) {
                String birthday = facebookUser.getBirthday();
                Studier studier = Studier.createStudierWithoutId(facebookUser.getName(),
                        Gender.valueOf(facebookUser.getGender().toUpperCase()),
                        Integer.parseInt(birthday.substring(birthday.length()-4, birthday.length())),
                        new Url(""),
                        new Url(facebookUser.getLink()));
                account = Account.createAccountFromFacebook(studier.getStudierId(),
                        facebookId, new Email(facebookUser.getEmail()));
                studierRepository.save(studier);
                accountRepository.save(account);
            }
            Session session = Session.createSession(account.getStudierId());
            sessionRepository.save(session);
            return session.genToken(sessionConfig.getTokenSecret());
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
        private String link;
    }
}
