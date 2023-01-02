package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
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
        this.value = allowedSupplyItem.getValue().toSupplyItemValue(operator, value);
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
