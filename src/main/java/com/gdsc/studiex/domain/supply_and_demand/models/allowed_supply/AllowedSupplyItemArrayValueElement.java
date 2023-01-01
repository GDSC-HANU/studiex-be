package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AllowedSupplyItemArrayValueElement {
    private Id id;
    private String value;

    @Builder(builderMethodName = "newAllowedSupplyItemArrayValueElement", builderClassName = "NewAllowedSupplyItemArrayValueElement")
    public AllowedSupplyItemArrayValueElement(String value) {
        this.id = Id.generateRandom();
        if (value == null)
            throw new InvalidInputException("AllowedSupplyItemArrayValueElement.value must be not null");
        this.value = value;
    }
}
