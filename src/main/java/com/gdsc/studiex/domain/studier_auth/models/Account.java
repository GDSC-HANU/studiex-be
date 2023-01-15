package com.gdsc.studiex.domain.studier_auth.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Getter;

@Getter
public class Account {
    private Id studierId;
    private String facebookId;
    private Email email;
    private Password password;

    private Account(Id studierId, String facebookId, Email email, Password password) {
        this.facebookId = facebookId;
        this.studierId = studierId;
        this.email = email;
        this.password = password;
    }

    public static Account createAccountFromFacebook(Id studierId, String facebookId, Email email) {
        return new Account(studierId, facebookId, email, null);
    }

}
