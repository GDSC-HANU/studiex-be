package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemExactValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;

public class DemandItemExactValue implements DemandItemValue {
    private Id allowedSupplyItemArrayValueId;

    private DemandItemExactValue() {}

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

    @Override
    public boolean match(DemandItemOperator operator, SupplyItem supplyItem) {
        if (!supplyItem.getOperator().equals(SupplyItemOperator.IS))
            return false;
        final SupplyItemExactValue supplyItemExactValue = (SupplyItemExactValue) supplyItem.getValue();
        return allowedSupplyItemArrayValueId.equals(supplyItemExactValue.getAllowedSupplyItemArrayValueId());
    }

    @Override
    public int totalCriteria(DemandItemOperator operator) {
        return 1;
    }

    @Override
    public int matchCriteria(DemandItemOperator operator, SupplyItem supplyItem) {
        if (!match(operator, supplyItem))
            return 0;
        return 1;
    }

    @Override
    public boolean canBeUsedWith(DemandItemOperator operator) {
        return operator.equals(DemandItemOperator.IS);
    }
}
