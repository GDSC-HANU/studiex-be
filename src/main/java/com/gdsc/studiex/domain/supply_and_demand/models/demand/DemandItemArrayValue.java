package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
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
}
