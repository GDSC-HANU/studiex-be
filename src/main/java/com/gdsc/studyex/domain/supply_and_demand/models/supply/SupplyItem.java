package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
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
                      String description) throws InvalidInputException {
        this.allowedSupplyItemIndex = allowedSupplyItemIndex;
        this.operator = operator;
        this.value = value;
        this.description = description;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (operator == null)
            throw new InvalidInputException("Supply.operator must not be null");
        if (value == null)
            throw new InvalidInputException("Supply.value must not be null");
        if (description == null)
            throw new InvalidInputException("Supply.description must not be null");
    }
}
