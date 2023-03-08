package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AllowedSupplyMongoRepository implements AllowedSupplyRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    private final String COLLECTION = "allowedSupplies";
    @Override
    public void save(AllowedSupply allowedSupply) {
        try {
            final Query query = Query.query(
                    Criteria
                            .where("_id")
                            .is(allowedSupply.getId().toString())
                            .and("version")
                            .is(allowedSupply.getVersion())
            );
            allowedSupply.increaseVersion();
            final Update update = new Update();
            update.set("_id", allowedSupply.getId().toObjectId());
            update.set("id", allowedSupply.getId().toString());
            update.set("subjectName", allowedSupply.getSubjectName());
            final Object allowedSupplyItems = CustomObjectMapper.convertObjectClass(
                    allowedSupply.getAllowedSupplyItems(),
                    Object.class
            );
            update.set("allowedSupplyItems", allowedSupplyItems);
            update.set("version", allowedSupply.getVersion());
            mongoTemplate.upsert(
                    query,
                    update,
                    COLLECTION
            );
        } catch (DuplicateKeyException e) {
            throw new InvalidInputException("subjectName already exists: " + allowedSupply.getSubjectName());
        }
    }

    @Override
    public List<AllowedSupply> findBySubjectNames(List<String> subjectNames) {
        final Query query = Query.query(
                Criteria
                        .where("subjectName")
                        .in(subjectNames)
        );
        final List<String> result = mongoTemplate.find(query, String.class, COLLECTION);
        return result.stream()
                .map(str -> CustomObjectMapper.deserialize(str, AllowedSupply.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AllowedSupply> findPaging(int page, int perPage) {
        final Query query = new Query();
        query.skip(perPage * (page - 1));
        query.limit(perPage);
        final List<String> result = mongoTemplate.find(query, String.class, COLLECTION);
        return result.stream()
                .map(str -> CustomObjectMapper.deserialize(str, AllowedSupply.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AllowedSupply> findByIds(List<Id> allowedSupplyIds) {
        final Query query = new Query(
                Criteria.where("_id")
                        .in(allowedSupplyIds.stream()
                                .map(Id::toObjectId)
                                .collect(Collectors.toList()))
        );
        final List<String> result = mongoTemplate.find(query, String.class, COLLECTION);
        return result.stream()
                .map(str -> CustomObjectMapper.deserialize(str, AllowedSupply.class))
                .collect(Collectors.toList());
    }

    @Override
    public AllowedSupply findById(Id id) {
        final Query query = new Query(
                Criteria.where("id")
                        .is(id.toString())
        );
        final String result = mongoTemplate.findOne(query, String.class, COLLECTION);
        return CustomObjectMapper.deserialize(result, AllowedSupply.class);
    }
}
