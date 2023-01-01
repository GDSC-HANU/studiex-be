package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;

public interface AllowedSupplyItemValue {
    public SupplyItemValue toSupplyItemValue(SupplyItemOperator supplyItemOperator,
                                             SuppliesDTO.SupplyItemValueDTO supplyItemValue) throws InvalidInputException;
    public AllowedSupplyDTO.AllowedSupplyItemValueDTO toAllowedSupplyItemValueDTO();

    public boolean canBeUsedWith(AllowedSupplyOperator operator);
}
