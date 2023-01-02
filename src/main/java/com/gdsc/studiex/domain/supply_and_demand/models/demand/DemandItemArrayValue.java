package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;

import java.util.List;

public class DemandItemArrayValue implements DemandItemValue {
    private List<Id> allowedSupplyItemArrayValueIds;

    @Builder(builderMethodName = "newDemandItemArrayValueBuilder", builderClassName = "NewDemandItemArrayValueBuilder")
    public DemandItemArrayValue(List<Id> allowedSupplyItemArrayValueIds) {
        this.allowedSupplyItemArrayValueIds = allowedSupplyItemArrayValueIds;
    }
}
