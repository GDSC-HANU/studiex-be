package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.models.StringEntity;
import com.gdsc.studiex.domain.studier.repositories.StringEntityRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public abstract class StringEntityMongoRepository implements StringEntityRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    protected abstract String collectionName();

    @Override
    public List<StringEntity> findByValues(Collection<String> values) {
        final Query query = Query.query(
                Criteria.where("value")
                        .in(values)
        );
        final List<String> jsons = mongoTemplate.find(query, String.class, collectionName());
        return jsons.stream()
                .map(str -> CustomObjectMapper.deserialize(str, StringEntity.class))
                .collect(Collectors.toList());
    }
}
