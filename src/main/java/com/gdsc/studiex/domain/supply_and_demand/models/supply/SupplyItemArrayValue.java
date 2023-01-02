package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class SupplyItemArrayValue implements SupplyItemValue {
    private List<Id> allowedSupplyItemArrayValueIds;

    @Builder(builderMethodName = "newSupplyItemArrayValue", builderClassName = "NewSupplyItemArrayValue")
    public SupplyItemArrayValue(List<Id> allowedSupplyItemArrayValueIds) {
        this.allowedSupplyItemArrayValueIds = allowedSupplyItemArrayValueIds;
    }

    private SupplyItemArrayValue() {

    }
}
