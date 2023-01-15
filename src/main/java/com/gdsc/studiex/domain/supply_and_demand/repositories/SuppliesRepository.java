package com.gdsc.studiex.domain.supply_and_demand.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;

import java.util.List;

public interface SuppliesRepository {
    public void save(Supplies supplies);

    public Supplies findByStudierId(Id studierId);

    public List<Supplies> findSuppliesContains(List<Id> allowedSupplyIds, List<Id> studierIds);
}
