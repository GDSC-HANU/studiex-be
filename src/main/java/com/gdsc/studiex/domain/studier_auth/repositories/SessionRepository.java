package com.gdsc.studiex.domain.studier_auth.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.models.Session;

public interface SessionRepository {
    public void save(Session session);

    Session findBySessionId(Id sessionId);
}
