package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SupplyItem {
    private int allowedSupplyItemIndex;
    private SupplyItemOperator operator;
    private SupplyItemValue value;
    private String description;

    @Builder(builderMethodName = "fromAllowedSupplyBuilder", builderClassName = "FromAllowedSupplyBuilder")
    public SupplyItem(String key,
                      SupplyItemOperator operator,
                      SuppliesDTO.SupplyItemValueDTO value,
                      String description,
                      AllowedSupply allowedSupply) throws InvalidInputException {
        final AllowedSupplyItem allowedSupplyItem = allowedSupply.findItemByKey(key);
        if (allowedSupplyItem == null)
            throw new InvalidInputException("There are no Allowed Supply Item with key: " + key);
        if (!allowedSupplyItem.canBeUsedWith(operator))
            throw new InvalidInputException(String.format("Cannot use operator %s for the key %s", operator, key));
        this.allowedSupplyItemIndex = allowedSupply.findItemIndexByKey(key);
        this.operator = operator;
        this.value = allowedSupplyItem.getValue().toSupplyItemValue(allowedSupplyItem.getOperator(), operator, value);
        this.description = description;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (operator == null)
            throw new InvalidInputException("SupplyItem.operator must not be null");
        if (value == null)
            throw new InvalidInputException("SupplyItem.value must not be null");
        if (description == null)
            throw new InvalidInputException("SupplyItem.description must not be null");
    }
}
