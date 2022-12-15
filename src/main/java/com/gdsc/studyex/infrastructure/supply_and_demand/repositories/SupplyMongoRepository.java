package com.gdsc.studyex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studyex.domain.supply_and_demand.repositories.SupplyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SupplyMongoRepository implements SupplyRepository {
    @Override
    public void save(Supply supply) {

    }
}
