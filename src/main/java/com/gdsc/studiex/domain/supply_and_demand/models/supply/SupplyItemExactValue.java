package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SupplyItemExactValue implements SupplyItemValue {
    private Id allowedSupplyItemArrayValueId;

    @Builder(builderMethodName = "newSupplyItemExactValueBuilder", builderClassName = "NewSupplyItemExactValueBuilder")
    public SupplyItemExactValue(Id allowedSupplyItemArrayValueId) {
        this.allowedSupplyItemArrayValueId = allowedSupplyItemArrayValueId;
    }

    private SupplyItemExactValue() {
    }

    @Override
    public SuppliesDTO.SupplyItemValueDTO buildSupplyItemValueDTO(AllowedSupplyItemValue allowedSupplyItemValue) {
        final AllowedSupplyItemArrayValue allowedSupplyItemArrayValue = CustomObjectMapper.convertObjectClass(allowedSupplyItemValue, AllowedSupplyItemArrayValue.class);
        for (AllowedSupplyItemArrayValueElement element : allowedSupplyItemArrayValue.getElements())
            if (allowedSupplyItemArrayValueId.equals(element.getId()))
                return new SuppliesDTO.SupplyItemExactValueDTO(element.getValue());
        return null;
    }
}
