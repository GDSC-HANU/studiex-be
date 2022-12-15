package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Supply {
    private Id allowedSupplyId;
    private List<SupplyItem> supplyItems;
    private boolean active;

    @Builder(builderMethodName = "newSupplyBuilder", builderClassName = "NewSupplyBuilder")
    public Supply(Id allowedSupplyId, List<SupplyItem> supplyItems, boolean active) {
        this.allowedSupplyId = allowedSupplyId;
        if (supplyItems == null)
            supplyItems = new ArrayList<>();
        this.supplyItems = supplyItems;
        this.active = active;
        validate();
    }

    private void validate() {

    }
}