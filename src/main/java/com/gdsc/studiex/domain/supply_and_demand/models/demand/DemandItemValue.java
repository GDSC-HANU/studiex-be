package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;

public interface DemandItemValue {
    public DemandsDTO.DemandItemValueDTO buildeDemandItemValueDto(AllowedSupplyItemValue allowedSupplyItemValue);
}
