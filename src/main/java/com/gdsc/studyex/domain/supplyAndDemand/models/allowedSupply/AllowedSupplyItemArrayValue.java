package com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply;

import lombok.Builder;

import java.util.List;

public class AllowedSupplyItemArrayValue extends AllowedSupplyItemValue {
    private List<String> values;

    @Builder(builderMethodName = "newAllowedSupplyItemArrayBuilder", builderClassName = "NewAllowedSupplyItemArrayValue")
    public AllowedSupplyItemArrayValue(List<String> values) {
        this.values = values;
    }
}
