package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.models.StringEntity;
import com.gdsc.studiex.domain.studier.repositories.StringEntitiesRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class StringEntitiesMongoRepository implements StringEntitiesRepository {
    private final String COLLECTION = "studier.stringsEntities";
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public StringEntities find(StringEntities.Type type) {
        final List<StringEntities> stringEntities = mongoTemplate.findAll(StringEntities.class, COLLECTION);
        for (StringEntities entities : stringEntities)
            if (entities.getType().equals(type))
                return entities;
        return StringEntities.empty(type);
    }


    @Override
    public void save(StringEntities stringEntities) {
        final Query query = Query.query(
                Criteria
                        .where("type")
                        .is(stringEntities.getType().name())
        );
        final Update update = new Update();
        update.set("data", stringEntities.getData());
        update.set("type", stringEntities.getType());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION
        );
    }
}
