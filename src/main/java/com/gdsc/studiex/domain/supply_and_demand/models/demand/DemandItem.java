package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DemandItem {
    private int allowedSupplyItemIndex;
    private DemandItemOperator operator;
    private DemandItemValue value;
    private String description;

    @Builder(builderMethodName = "fromAllowedSupplyBuilder", builderClassName = "FromAllowedSupplyBuilder")
    public DemandItem(String key,
                      DemandItemOperator operator,
                      DemandsDTO.DemandItemValueDTO value,
                      String description,
                      AllowedSupply allowedSupply) throws InvalidInputException {
        final AllowedSupplyItem allowedSupplyItem = allowedSupply.findItemByKey(key);
        if (allowedSupplyItem == null)
            throw new InvalidInputException("There are no Allowed Supply Item with key: " + key);
        if (!allowedSupplyItem.canBeUsedWith(operator))
            throw new InvalidInputException(String.format("Cannot use operator %s for the key %s", operator, key));
        this.allowedSupplyItemIndex = allowedSupply.findItemIndexByKey(key);
        this.operator = operator;
        this.value = allowedSupplyItem.getValue().toDemandItemValue(allowedSupplyItem.getOperator(), operator, value);
        this.description = description;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (operator == null)
            throw new InvalidInputException("DemandItem.operator must not be null");
        if (value == null)
            throw new InvalidInputException("DemandItem.value must not be null");
        if (description == null)
            throw new InvalidInputException("DemandItem.description must not be null");
    }
}
