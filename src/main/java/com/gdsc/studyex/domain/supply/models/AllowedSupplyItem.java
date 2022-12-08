package com.gdsc.studyex.domain.supply.models;

public class AllowedSupplyItem {
    private int index;
    private String key;
    private OperatorAllowedSupply operator;
    private AllowedSupplyItemValue allowedSupplyItemValue;
    private String description;

    private AllowedSupplyItem(int index,
                              String key,
                              OperatorAllowedSupply operator,
                              AllowedSupplyItemValue allowedSupplyItemValue,
                              String description) {
        this.index = index;
        this.key = key;
        this.operator = operator;
        this.allowedSupplyItemValue = allowedSupplyItemValue;
        this.description = description;
    }

    public static AllowedSupplyItem create(int index,
                                           String key,
                                           OperatorAllowedSupply operator,
                                           AllowedSupplyItemValue allowedSupplyItemValue,
                                           String description) {
        return new AllowedSupplyItem(index,
                key,
                operator,
                allowedSupplyItemValue,
                description);
    }
}
