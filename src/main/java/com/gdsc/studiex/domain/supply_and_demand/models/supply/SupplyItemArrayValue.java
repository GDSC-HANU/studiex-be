package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class SupplyItemArrayValue implements SupplyItemValue {
    private List<Id> allowedSupplyItemArrayValueIds;

    @Builder(builderMethodName = "newSupplyItemArrayValueBuilder", builderClassName = "NewSupplyItemArrayValueBuilder")
    public SupplyItemArrayValue(List<Id> allowedSupplyItemArrayValueIds) {
        this.allowedSupplyItemArrayValueIds = allowedSupplyItemArrayValueIds;
    }

    private SupplyItemArrayValue() {

    }

    @Override
    public SuppliesDTO.SupplyItemValueDTO buildSupplyItemValueDTO(AllowedSupplyItemValue allowedSupplyItemValue) {
        final AllowedSupplyItemArrayValue allowedSupplyItemArrayValue = CustomObjectMapper.convertObjectClass(allowedSupplyItemValue, AllowedSupplyItemArrayValue.class);
        Map<Id, String> allowedSupplyItemValueElementMap = allowedSupplyItemArrayValue.getElements().stream()
                .collect(Collectors.toMap(
                        AllowedSupplyItemArrayValueElement::getId,
                        AllowedSupplyItemArrayValueElement::getValue));
        return SuppliesDTO.SupplyItemArrayValueDTO.newSupplyItemArrayValueDTO()
                .value(allowedSupplyItemArrayValueIds.stream()
                        .map(allowedSupplyItemValueElementMap::get)
                        .collect(Collectors.toList()))
                .build();
    }
}
