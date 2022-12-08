package com.gdsc.studyex.domain.supply.models;

import com.gdsc.studyex.domain.share.models.VersioningDomainObject;

public class SupplyItem extends VersioningDomainObject {
    private int allowedSupplyItemIndex;
    private OperatorSupply operator;
    private SupplyItemValue value;
    private String description;

    private SupplyItem(int allowedSupplyItemIndex,
                       OperatorSupply operator,
                       SupplyItemValue value,
                       String description,
                       long version) {
        super(version);
        this.allowedSupplyItemIndex = allowedSupplyItemIndex;
        this.operator = operator;
        this.value = value;
        this.description = description;
    }

    public static SupplyItem create(int allowedSupplyItemIndex,
                                    OperatorSupply operator,
                                    SupplyItemValue value,
                                    String description) {
        return new SupplyItem(allowedSupplyItemIndex,
                operator,
                value,
                description,
                0);
    }
}
