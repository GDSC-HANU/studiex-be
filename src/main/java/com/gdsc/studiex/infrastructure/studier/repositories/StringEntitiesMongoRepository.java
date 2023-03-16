package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.models.StringEntity;
import com.gdsc.studiex.domain.studier.repositories.StringEntitiesRepository;
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

    @Override
    public List<StringEntity> findByIds(Set<Id> ids) {
        List<StringEntity> allStringEntity = find().getData();
        List<StringEntity> result = new ArrayList<>();
        if(ids != null) {
            for(Id id: ids) {
                for (StringEntity stringEntity : allStringEntity) {
                    if (stringEntity.getId().equals(id)) {
                        result.add(stringEntity);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void save(StringEntities stringEntities) {
        Query query = new Query();
        Update update = new Update();
        if(mongoTemplate.count(query, collectionName()) == 0) {
            update.set("_id", Id.generateRandom().toString());
        }
        for(int i = 0; i < stringEntities.getData().size(); i++) {
            StringEntity stringEntity = stringEntities.getData().get(i);
            update.set(stringEntity.getId().toString(), stringEntity.getValue());
        }
        mongoTemplate.upsert(
                query,
                update,
                collectionName()
        );
    }
}
