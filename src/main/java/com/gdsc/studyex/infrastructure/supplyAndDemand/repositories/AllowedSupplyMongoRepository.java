package com.gdsc.studyex.infrastructure.supplyAndDemand.repositories;

import com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply.AllowedSupply;
import com.gdsc.studyex.domain.supplyAndDemand.repositories.AllowedSupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class AllowedSupplyMongoRepository implements AllowedSupplyRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private Update buildUpdate(AllowedSupply allowedSupply) {
        allowedSupply.increaseVersion();
        final Update update = new Update();
        update.set("_id", allowedSupply.getId().toObjectId());
        update.set("subjectName", allowedSupply.getSubjectName());
        update.set("allowedSupplyItems", allowedSupply.getAllowedSupplyItems());
        update.set("version", allowedSupply.getVersion());
        return update;
    }

    private Query buildQuery(AllowedSupply allowedSupply) {
        return Query.query(
                Criteria
                        .where("_id")
                        .is(allowedSupply.getId().toString())
                        .and("version")
                        .is(allowedSupply.getVersion())
        );
    }

    @Override
    public void save(AllowedSupply allowedSupply) {
        mongoTemplate.upsert(
                buildQuery(allowedSupply),
                buildUpdate(allowedSupply),
                AllowedSupply.class
        );
    }
}
