package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.models.Id;

public class AuthorizeStudierService {
    public Id authorize(String accessToken) {
        // TODO: implement
        return Id.parse(accessToken);
    }
}
