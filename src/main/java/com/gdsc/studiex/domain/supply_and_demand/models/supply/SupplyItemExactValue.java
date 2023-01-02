package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;

public class SupplyItemExactValue implements SupplyItemValue {
    private Id allowedSupplyItemArrayValueId;

    @Builder(builderMethodName = "newSupplyItemExactValueBuilder", builderClassName = "NewSupplyItemExactValueBuilder")
    public SupplyItemExactValue(Id allowedSupplyItemArrayValueId) {
        this.allowedSupplyItemArrayValueId = allowedSupplyItemArrayValueId;
    }
}
