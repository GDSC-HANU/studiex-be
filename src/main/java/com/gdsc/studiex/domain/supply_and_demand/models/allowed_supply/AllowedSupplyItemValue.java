package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;

public interface AllowedSupplyItemValue {
    public SupplyItemValue convertToSupplyItemValue(SupplyItemOperator supplyItemOperator, Object supplyItemValue) throws InvalidInputException;
}
