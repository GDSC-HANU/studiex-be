package com.gdsc.studiex.domain.supply_and_demand.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;

import java.util.List;

public interface DemandsRepository {
    public void save(Demands demands);

    public Demands findByStudierId(Id studierId);

    public List<Demands> findDemandsContains(List<Id> allowedSupplyIds);
}
