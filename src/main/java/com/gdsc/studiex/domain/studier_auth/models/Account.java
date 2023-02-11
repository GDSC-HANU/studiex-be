package com.gdsc.studiex.domain.studier_auth.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Account {
    private Id studierId;
    private String facebookId;
    private Email email;
    private Password password;

    private Account() {
    }

    private Account(Id studierId, String facebookId, Email email, Password password) {
        this.facebookId = facebookId;
        this.studierId = studierId;
        this.email = email;
        this.password = password;
    }

    @Builder(builderMethodName = "newAccountBuilder", builderClassName = "NewAccountBuilder")
    public Account(Id studierId, String facebookId, Email email) {
        this.facebookId = facebookId;
        this.studierId = studierId;
        this.email = email;
        this.password = null;
    }

}
