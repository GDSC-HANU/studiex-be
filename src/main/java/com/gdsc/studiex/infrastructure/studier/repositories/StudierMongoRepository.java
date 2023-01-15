package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class StudierMongoRepository implements StudierRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTIONS = "studier";

    @Override
    public Studier findByStudierId(Id studierId) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(studierId.toString())
        );
        final String studier = mongoTemplate.findOne(query, String.class, COLLECTIONS);
        return (studier == null)? null : CustomObjectMapper.deserialize(studier, Studier.class);
    }

    @Override
    public void save(Studier studier) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(studier.getStudierId().toString())
        );
        Update update = new Update();
        update.set("_id", studier.getStudierId().toObjectId());
        update.set("studierId", studier.getStudierId().toString());
        update.set("name", studier.getName());
        update.set("gender", studier.getGender());
        update.set("yob", studier.getYob());
        update.set("avatar", studier.getAvatar().toString());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTIONS
        );
    }
}
