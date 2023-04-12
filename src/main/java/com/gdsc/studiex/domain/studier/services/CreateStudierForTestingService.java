package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Gender;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierPrivacy;
import com.gdsc.studiex.domain.studier.models.Url;
import com.gdsc.studiex.domain.studier.repositories.StudierPrivacyRepository;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import com.gdsc.studiex.domain.studier_auth.models.Account;
import com.gdsc.studiex.domain.studier_auth.models.Email;
import com.gdsc.studiex.domain.studier_auth.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateStudierForTestingService {
    private final StudierRepository studierRepository;
    private final AccountRepository accountRepository;
    private final StudierPrivacyRepository studierPrivacyRepository;

    public Id createStudierForTesting(CreateStudierForTestingInput input) {
        final Studier studier = Studier.newStudierBuilder()
                .name(input.name)
                .gender(input.gender)
                .yob(input.yob)
                .avatar(input.avatar)
                .build();
        final StudierPrivacy studierPrivacy = StudierPrivacy.defaultStudierPrivacy(studier.getStudierId());
        final Account account = Account.newAccountBuilder()
                .studierId(studier.getStudierId())
                .email(input.email)
                .build();
        studierRepository.save(studier);
        studierPrivacyRepository.save(studierPrivacy);
        accountRepository.save(account);
        return studier.getStudierId();
    }

    public static class CreateStudierForTestingInput {
        public String name;
        public Gender gender;
        public int yob;
        public Url avatar;
        public Email email;
    }
}
