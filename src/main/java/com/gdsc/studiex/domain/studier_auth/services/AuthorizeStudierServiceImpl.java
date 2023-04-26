package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.exceptions.UnauthorizedException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.config.SessionConfig;
import com.gdsc.studiex.domain.studier_auth.models.Session;
import com.gdsc.studiex.domain.studier_auth.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizeStudierServiceImpl implements AuthorizeStudierService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private SessionConfig sessionConfig;
    public Id authorize(String accessToken) throws BusinessLogicException {
        Id sessionId = Session.verifyTokenAndGetSessionId(accessToken, sessionConfig.getTokenSecret());
        Session session = sessionRepository.findBySessionId(sessionId);
        if (session == null || session.invalidated()) {
            throw new UnauthorizedException();
        }
        return session.getStudierId();
    }
}
