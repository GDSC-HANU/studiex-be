package com.gdsc.studyex.domain.supply_and_demand.repositories;

import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supply;

public interface SuppliesRepository {
    public void save(Supplies supplies);
}
