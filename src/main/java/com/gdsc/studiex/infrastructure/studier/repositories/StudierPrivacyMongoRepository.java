package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StudierPrivacy;
import com.gdsc.studiex.domain.studier.repositories.StudierPrivacyRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class StudierPrivacyMongoRepository implements StudierPrivacyRepository {
    private static final String COLLECTIONS = "studierPrivacy";
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public StudierPrivacy findByStudierId(Id studierId) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(studierId.toString())
        );
        final String studierPrivacy = mongoTemplate.findOne(query, String.class, COLLECTIONS);
        return (studierPrivacy == null) ? null : CustomObjectMapper.deserialize(studierPrivacy, StudierPrivacy.class);
    }

    @Override
    public void save(StudierPrivacy studierPrivacy) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(studierPrivacy.getStudierId().toString())
        );
        Update update = new Update();
        update.set("_id", studierPrivacy.getStudierId().toObjectId());
        update.set("studierId", studierPrivacy.getStudierId().toString());
        update.set("gender", studierPrivacy.getGender());
        update.set("yob", studierPrivacy.getYob());
        update.set("qualifications", studierPrivacy.getQualifications());
        update.set("learningStyles", studierPrivacy.getLearningStyles());
        update.set("lifeGoals", studierPrivacy.getLifeGoals());
        update.set("majors", studierPrivacy.getMajors());
        update.set("dislikes", studierPrivacy.getDislikes());
        update.set("likes", studierPrivacy.getLikes());
        update.set("personalities", studierPrivacy.getPersonalities());
        update.set("coordinates", studierPrivacy.getCoordinates());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTIONS
        );
    }
}
