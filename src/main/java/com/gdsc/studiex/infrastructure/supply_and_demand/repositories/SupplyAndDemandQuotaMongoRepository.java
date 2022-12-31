package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota.SupplyAndDemandQuota;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesQuotaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SupplyAndDemandQuotaMongoRepository implements SuppliesQuotaRepository {
    @Override
    public SupplyAndDemandQuota get() {
        // TODO: implement
        return SupplyAndDemandQuota.newSuppliesQuota()
                .maxActiveDemand(3)
                .maxActiveSupply(3)
                .build();
    }
}
