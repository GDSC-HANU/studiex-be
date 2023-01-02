package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;

public class DemandItemExactValue implements DemandItemValue {
    private Id allowedSupplyItemArrayValueId;

    @Builder(builderMethodName = "newDemandItemExactValueBuilder", builderClassName = "newDemandItemExactValueBuilder")
    public DemandItemExactValue(Id allowedSupplyItemArrayValueId) {
        this.allowedSupplyItemArrayValueId = allowedSupplyItemArrayValueId;
    }
}
