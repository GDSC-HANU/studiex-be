package com.gdsc.studyex.infrastructure.supplyAndDemand.repositories;

import com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply.AllowedSupply;
import com.gdsc.studyex.domain.supplyAndDemand.repositories.AllowedSupplyRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AllowedSupplyMongoRepository implements AllowedSupplyRepository {
    private MongoTemplate mongoTemplate;
    @Override
    public void save(AllowedSupply allowedSupply) {

    }
}
