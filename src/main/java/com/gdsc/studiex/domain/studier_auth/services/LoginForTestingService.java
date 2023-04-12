package com.gdsc.studiex.domain.studier_auth.services;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.config.SessionConfig;
import com.gdsc.studiex.domain.studier_auth.models.Session;
import com.gdsc.studiex.domain.studier_auth.repositories.SessionRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginForTestingService {
    @Autowired
    private SessionConfig sessionConfig;
    @Autowired
    private SessionRepository sessionRepository;

    public Output loginForTesting(Id studierId) {
        Session session = Session.createSession(studierId);
        sessionRepository.save(session);
        String accessToken = session.genToken(sessionConfig.getTokenSecret());
        return new Output(accessToken);
    }

    @AllArgsConstructor
    public static class Output {
        public String accessToken;
    }
}
