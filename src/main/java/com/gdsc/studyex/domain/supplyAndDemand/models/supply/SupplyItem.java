package com.gdsc.studyex.domain.supplyAndDemand.models.supply;

import lombok.Builder;

public class SupplyItem {
    private int allowedSupplyItemIndex;
    private SupplyItemOperator operator;
    private SupplyItemValue value;
    private String description;

    @Builder(builderMethodName = "newSupplyItemBuilder", builderClassName = "NewSupplyItemBuilder")
    public SupplyItem(int allowedSupplyItemIndex,
                      SupplyItemOperator operator,
                      SupplyItemValue value,
                      String description) {
        this.allowedSupplyItemIndex = allowedSupplyItemIndex;
        this.operator = operator;
        this.value = value;
        this.description = description;
    }
}
