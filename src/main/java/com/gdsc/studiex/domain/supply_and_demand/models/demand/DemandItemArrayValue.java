package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidDataException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemArrayValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DemandItemArrayValue implements DemandItemValue {
    private List<Id> allowedSupplyItemArrayValueIds;

    private DemandItemArrayValue() {}

    @Builder(builderMethodName = "newDemandItemArrayValueBuilder", builderClassName = "NewDemandItemArrayValueBuilder")
    public DemandItemArrayValue(List<Id> allowedSupplyItemArrayValueIds) {
        this.allowedSupplyItemArrayValueIds = allowedSupplyItemArrayValueIds;
    }

    @Override
    public DemandsDTO.DemandItemValueDTO buildeDemandItemValueDto(AllowedSupplyItemValue allowedSupplyItemValue) {
        final AllowedSupplyItemArrayValue allowedSupplyItemArrayValue = CustomObjectMapper.convertObjectClass(allowedSupplyItemValue, AllowedSupplyItemArrayValue.class);
        final Map<Id, String> allowedSupplyItemValueElementMap = allowedSupplyItemArrayValue.getElements().stream()
                .collect(Collectors.toMap(
                        AllowedSupplyItemArrayValueElement::getId,
                        AllowedSupplyItemArrayValueElement::getValue));
        return new DemandsDTO.DemandItemArrayValueDTO(allowedSupplyItemArrayValueIds.stream()
                .map(allowedSupplyItemValueElementMap::get)
                .collect(Collectors.toList()));
    }

    @Override
    public boolean match(DemandItemOperator operator, SupplyItem supplyItem) {
        final SupplyItemArrayValue supplyItemArrayValue = (SupplyItemArrayValue) supplyItem.getValue();
        switch (operator) {
            case INCLUDES_ALL:
                for (Id allowedSupplyItemArrayValueId : allowedSupplyItemArrayValueIds)
                    if (!supplyItemArrayValue.getAllowedSupplyItemArrayValueIds().contains(allowedSupplyItemArrayValueId))
                        return false;
                return true;
            case INCLUDES_ANY:
                int cnt = 0;
                for (Id allowedSupplyItemArrayValueId : allowedSupplyItemArrayValueIds)
                    if (supplyItemArrayValue.getAllowedSupplyItemArrayValueIds().contains(allowedSupplyItemArrayValueId))
                        cnt++;
                return cnt >= 1;
            default:
                throw new InvalidDataException("Invalid operator for demand item value");
        }
    }

    @Override
    public int totalCriteria(DemandItemOperator operator) {
        switch (operator) {
            case INCLUDES_ALL:
                return 1;
            case INCLUDES_ANY:
                return allowedSupplyItemArrayValueIds.size();
            default:
                throw new InvalidDataException("Invalid operator for demand item value");
        }
    }

    @Override
    public int matchCriteria(DemandItemOperator operator, SupplyItem supplyItem) {
        final SupplyItemArrayValue supplyItemArrayValue = (SupplyItemArrayValue) supplyItem.getValue();
        switch (operator) {
            case INCLUDES_ALL:
                for (Id allowedSupplyItemArrayValueId : allowedSupplyItemArrayValueIds)
                    if (!supplyItemArrayValue.getAllowedSupplyItemArrayValueIds().contains(allowedSupplyItemArrayValueId))
                        return 0;
                return 1;
            case INCLUDES_ANY:
                int cnt = 0;
                for (Id allowedSupplyItemArrayValueId : allowedSupplyItemArrayValueIds)
                    if (supplyItemArrayValue.getAllowedSupplyItemArrayValueIds().contains(allowedSupplyItemArrayValueId))
                        cnt++;
                return cnt;
            default:
                throw new InvalidDataException("Invalid operator for demand item value");
        }
    }

    @Override
    public boolean canBeUsedWith(DemandItemOperator operator) {
        return operator.equals(DemandItemOperator.INCLUDES_ALL)
                || operator.equals(DemandItemOperator.INCLUDES_ANY);
    }
}
