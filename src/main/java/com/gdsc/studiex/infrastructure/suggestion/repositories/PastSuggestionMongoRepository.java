package com.gdsc.studiex.infrastructure.suggestion.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.suggestion.models.PastSuggestion;
import com.gdsc.studiex.domain.suggestion.repositories.PastSuggestionRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PastSuggestionMongoRepository implements PastSuggestionRepository {
    private final String COLLECTION = "suggestion.pastSuggestion";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public PastSuggestion findByStudierId(Id studierId) {
        final Query query = new Query(
                Criteria.where("_id")
                        .is(studierId.toString())
        );
        final String result = mongoTemplate.findOne(query, String.class, COLLECTION);
        return CustomObjectMapper.deserialize(result, PastSuggestion.class);
    }

    @Override
    public void save(PastSuggestion pastSuggestion) {
        final Query query = Query.query(
                Criteria
                        .where("_id")
                        .is(pastSuggestion.getStudierId().toString())
        );
        final Update update = new Update();
        update.set("_id", pastSuggestion.getStudierId().toObjectId());
        update.set("studierId", pastSuggestion.getStudierId().toString());
        final Object suggestions = CustomObjectMapper.convertObjectClass(
                pastSuggestion.getSuggestions(),
                Object.class
        );
        update.set("suggestions", suggestions);
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION
        );
    }
}
