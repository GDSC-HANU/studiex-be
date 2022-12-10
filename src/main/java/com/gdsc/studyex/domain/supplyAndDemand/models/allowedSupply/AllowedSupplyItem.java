package com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply;

import lombok.Builder;

public class AllowedSupplyItem {
    private int index;
    private String key;
    private AllowedSupplyOperator operator;
    private AllowedSupplyItemValue allowedSupplyItemValue;
    private String description;

    @Builder(builderMethodName = "newAllowedSupplyItemBuilder", builderClassName = "NewAllowedSupplyItemBuilder")
    public AllowedSupplyItem(int index, String key, AllowedSupplyOperator operator, AllowedSupplyItemValue allowedSupplyItemValue, String description) {
        this.index = index;
        this.key = key;
        this.operator = operator;
        this.allowedSupplyItemValue = allowedSupplyItemValue;
        this.description = description;
    }
}
