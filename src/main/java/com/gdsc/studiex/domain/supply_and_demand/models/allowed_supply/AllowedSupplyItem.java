package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AllowedSupplyItem {
    private String key;
    private AllowedSupplyOperator operator;
    private AllowedSupplyItemValue value;
    private String description;
    private boolean required;

    @Builder(builderMethodName = "newAllowedSupplyItemBuilder", builderClassName = "NewAllowedSupplyItemBuilder")
    public AllowedSupplyItem(String key, AllowedSupplyOperator operator, AllowedSupplyItemValue value, String description, boolean required) throws InvalidInputException {
        this.key = key;
        this.operator = operator;
        this.value = value;
        this.description = description;
        this.required = required;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (key == null || key.trim().isEmpty())
            throw new InvalidInputException("AllowedSupplyItem.key must not be null");
        if (operator == null)
            throw new InvalidInputException("AllowedSupplyItem.operator must not be null");
        validateValue();
    }

    private void validateValue() throws InvalidInputException {
        if (value == null)
            throw new InvalidInputException("AllowedSupplyItem.value must not be null");
        if (!value.canBeUsedWith(operator))
            throw new InvalidInputException("Invalid AllowedSupplyItem.value for operator " + operator);
    }

    public boolean canBeUsedWith(SupplyItemOperator supplyItemOperator) {
        switch (supplyItemOperator) {
            case IS:
                return operator.equals(AllowedSupplyOperator.ONE_OF);
            case ARE:
                return operator.equals(AllowedSupplyOperator.MANY_OF);
            case BETWEEN:
                return operator.equals(AllowedSupplyOperator.BETWEEN);
        }
        return false;
    }

    public boolean canBeUsedWith(DemandItemOperator demandItemOperator) {
        switch (demandItemOperator) {
            case BETWEEN:
                return operator.equals(AllowedSupplyOperator.BETWEEN);
            case IS:
                return operator.equals(AllowedSupplyOperator.ONE_OF);
            case INCLUDES_ALL:
            case INCLUDES_ANY:
                return operator.equals(AllowedSupplyOperator.MANY_OF);
        }
        return false;
    }
}
