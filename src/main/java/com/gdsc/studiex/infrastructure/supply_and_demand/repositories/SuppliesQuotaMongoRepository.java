package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SuppliesQuotaMongoRepository implements SuppliesRepository {
    @Override
    public void save(Supplies supplies) {

    }
}
