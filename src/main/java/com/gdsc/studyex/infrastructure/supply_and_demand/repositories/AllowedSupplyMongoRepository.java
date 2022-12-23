package com.gdsc.studyex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studyex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AllowedSupplyMongoRepository implements AllowedSupplyRepository {
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void save(AllowedSupply allowedSupply) {
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
        update.set("subjectName", allowedSupply.getSubjectName());
        update.set("allowedSupplyItems", allowedSupply.getAllowedSupplyItems());
        update.set("version", allowedSupply.getVersion());
        mongoTemplate.upsert(
                query,
                update,
                AllowedSupply.class
        );
    }

    @Override
    public List<AllowedSupply> findBySubjectNames(List<String> subjectNames) {
        // TODO
        return null;
    }
}
