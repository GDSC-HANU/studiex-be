package com.gdsc.studyex.infrastructure.supplyAndDemand.repositories;

import com.gdsc.studyex.domain.supplyAndDemand.models.supply.Supply;
import com.gdsc.studyex.domain.supplyAndDemand.repositories.SupplyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SupplyMongoRepository implements SupplyRepository {
    @Override
    public void save(Supply supply) {

    }
}
