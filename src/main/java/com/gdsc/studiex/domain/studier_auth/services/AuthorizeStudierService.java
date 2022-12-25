package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.models.Id;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeStudierService {
    public Id authorize(String accessToken) {
        // TODO: implement
        return new Id(accessToken);
    }
}
