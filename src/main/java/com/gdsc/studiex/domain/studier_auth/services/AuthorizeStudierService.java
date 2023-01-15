package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.exceptions.UnauthorizedException;
import com.gdsc.studiex.domain.share.models.Id;

public interface AuthorizeStudierService {
    public Id authorize(String accessToken) throws BusinessLogicException;
}
