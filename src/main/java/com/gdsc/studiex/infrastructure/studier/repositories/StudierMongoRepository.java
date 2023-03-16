package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Language;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierSearchCriteria;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class StudierMongoRepository implements StudierRepository {
    private final String COLLECTIONS = "studier";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Studier findByStudierId(Id studierId) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(studierId.toString())
        );
        final String studier = mongoTemplate.findOne(query, String.class, COLLECTIONS);
        return (studier == null) ? null : CustomObjectMapper.deserialize(studier, Studier.class);
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
        update.set("avatar", studier.getAvatar() == null ? null : studier.getAvatar().toString());
        update.set("facebookLink", studier.getFacebookLink() == null ? null : studier.getFacebookLink().toString());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTIONS
        );
    }

    @Override
    public List<Studier> searchByCriteria(Id studerId,
                                          StudierSearchCriteria studierSearchCriteria) {
        final Studier studier = findByStudierId(studerId);
        if (studier == null)
            throw new InvalidInputException("Cannot find studier");
        final Query query = new Query();
        if (studierSearchCriteria.getQualificationIds() != null)
            query.addCriteria(Criteria.where("qualificationIds")
                    .in(studierSearchCriteria.getQualificationIds()));
        if (studierSearchCriteria.getAgeRange() != null)
            query.addCriteria(Criteria.where("yob")
                    .gte(studierSearchCriteria.getAgeRange().lowerboundYear())
                    .and("yob")
                    .lte(studierSearchCriteria.getAgeRange().upperboundYear()));
        if (studierSearchCriteria.getGender() != null)
            query.addCriteria(Criteria.where("gender")
                    .is(studierSearchCriteria.getGender()));
        if (studierSearchCriteria.getPersonalityIds() != null)
            query.addCriteria(Criteria.where("personalityIds")
                    .in(studierSearchCriteria.getPersonalityIds()));
        if (studierSearchCriteria.getLikeIds() != null)
            query.addCriteria(Criteria.where("likeIds")
                    .in(studierSearchCriteria.getLikeIds()));
        if (studierSearchCriteria.getDislikeIds() != null)
            query.addCriteria(Criteria.where("dislikeIds")
                    .in(studierSearchCriteria.getDislikeIds()));
        if (studierSearchCriteria.getDistance() != null)
            query.addCriteria(Criteria.where("coordinates")
                    .nearSphere(new Point(studier.getCoordinates().latitude(), studier.getCoordinates().longtitude()))
                    .maxDistance(studierSearchCriteria.getDistance().asDouble()));
        if (studierSearchCriteria.getLifeGoalIds() != null)
            query.addCriteria(Criteria.where("lifeGoalIds")
                    .in(studierSearchCriteria.getLifeGoalIds()));
        if (studierSearchCriteria.getLearningStyleIds() != null)
            query.addCriteria(Criteria.where("learningStyleIds")
                    .in(studierSearchCriteria.getLearningStyleIds()));
        if (studierSearchCriteria.getMajorIds() != null)
            query.addCriteria(Criteria.where("majorIds")
                    .in(studierSearchCriteria.getMajorIds()));
        query.addCriteria(Criteria.where("languagesForCommunication")
                .in(studierSearchCriteria.getLanguagesForCommunication()));
        final List<String> jsons = mongoTemplate.find(query, String.class, COLLECTIONS);
        return jsons.stream()
                .map(str -> CustomObjectMapper.deserialize(str, Studier.class))
                .collect(Collectors.toList());
    }
}
