package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.repositories.StringEntitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class StringEntitiesMongoRepository implements StringEntitiesRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    protected abstract String collectionName();

    @Override
    public StringEntities find() {
        final List<StringEntities> stringEntities = mongoTemplate.findAll(StringEntities.class);
        if (stringEntities.size() == 0)
            return StringEntities.empty();
        return stringEntities.get(0);
    }
}
