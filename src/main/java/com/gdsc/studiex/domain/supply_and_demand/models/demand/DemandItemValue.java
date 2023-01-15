package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;

public interface DemandItemValue {
    public DemandsDTO.DemandItemValueDTO buildeDemandItemValueDto(AllowedSupplyItemValue allowedSupplyItemValue);

    boolean match(DemandItemOperator operator, SupplyItem supplyItem);

    int totalCriteria(DemandItemOperator operator);

    int matchCriteria(DemandItemOperator operator, SupplyItem supplyItem);
}
