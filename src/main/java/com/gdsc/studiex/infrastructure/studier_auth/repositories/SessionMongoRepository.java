package com.gdsc.studiex.infrastructure.studier_auth.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.models.Session;
import com.gdsc.studiex.domain.studier_auth.repositories.SessionRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SessionMongoRepository implements SessionRepository {

    private final String COLLECTION = "session";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(Session session) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(session.getSessionId())
        );
        Update update = new Update();
        update.set("_id", session.getSessionId().toObjectId());
        update.set("sessionId", session.getSessionId().toString());
        update.set("studierId", session.getStudierId().toString());
        update.set("expireAt", session.getExpireAt().toString());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION
        );
    }

    public Session findBySessionId(Id sessionId) {
        Query query = Query.query(
                Criteria.where("sessionId")
                        .is(sessionId.toString())
        );
        final String session = mongoTemplate.findOne(query, String.class, COLLECTION);
        return session == null ? null : CustomObjectMapper.deserialize(session, Session.class);
    }
}
