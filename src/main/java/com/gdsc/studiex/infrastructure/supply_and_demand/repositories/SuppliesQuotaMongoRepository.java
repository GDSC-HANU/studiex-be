package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.supply_and_demand.models.supplies_quota.SuppliesQuota;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesQuotaRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import org.springframework.stereotype.Repository;

@Repository
public class SuppliesQuotaMongoRepository implements SuppliesQuotaRepository {
    @Override
    public SuppliesQuota get() {
        // TODO: implement
        return SuppliesQuota.newSuppliesQuota()
                .maxActiveQuota(3)
                .build();
    }
}
