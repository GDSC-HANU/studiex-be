package com.gdsc.studyex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studyex.domain.supply_and_demand.repositories.SuppliesRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SuppliesMongoRepository implements SuppliesRepository {
    @Override
    public void save(Supplies supplies) {

    }
}
