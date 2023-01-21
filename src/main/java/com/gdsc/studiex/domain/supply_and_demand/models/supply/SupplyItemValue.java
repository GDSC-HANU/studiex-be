package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;

public interface SupplyItemValue {
    public SuppliesDTO.SupplyItemValueDTO buildSupplyItemValueDTO(AllowedSupplyItemValue allowedSupplyItemValue);

    public boolean canBeUsedWith(SupplyItemOperator supplyItemOperator);
}
