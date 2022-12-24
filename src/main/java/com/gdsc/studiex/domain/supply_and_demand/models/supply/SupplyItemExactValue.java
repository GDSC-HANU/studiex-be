package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import lombok.Builder;

public class SupplyItemExactValue implements SupplyItemValue{
    private int allowedSupplyItemArrayValueIndex;

    @Builder(builderMethodName = "newSupplyItemExactValue", builderClassName = "NewSupplyItemExactValue")
    public SupplyItemExactValue(int allowedSupplyItemArrayValueIndex) {
        this.allowedSupplyItemArrayValueIndex = allowedSupplyItemArrayValueIndex;
    }
}
