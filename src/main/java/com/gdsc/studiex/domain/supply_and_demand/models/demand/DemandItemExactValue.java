package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;

public class DemandItemExactValue implements DemandItemValue {
    private Id allowedSupplyItemArrayValueId;

    @Builder(builderMethodName = "newDemandItemExactValueBuilder", builderClassName = "newDemandItemExactValueBuilder")
    public DemandItemExactValue(Id allowedSupplyItemArrayValueId) {
        this.allowedSupplyItemArrayValueId = allowedSupplyItemArrayValueId;
    }

    @Override
    public DemandsDTO.DemandItemValueDTO buildeDemandItemValueDto(AllowedSupplyItemValue allowedSupplyItemValue) {
        final AllowedSupplyItemArrayValue allowedSupplyItemArrayValue = CustomObjectMapper.convertObjectClass(allowedSupplyItemValue, AllowedSupplyItemArrayValue.class);
        for (AllowedSupplyItemArrayValueElement element : allowedSupplyItemArrayValue.getElements())
            if (allowedSupplyItemArrayValueId.equals(element.getId()))
                return new DemandsDTO.DemandItemExactValueDTO(element.getValue());
        return null;
    }
}
