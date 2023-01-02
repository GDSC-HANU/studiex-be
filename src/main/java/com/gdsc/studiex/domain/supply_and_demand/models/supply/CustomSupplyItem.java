package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import lombok.Builder;

public class CustomSupplyItem {
    private String key;
    private String value;
    private String description;

    @Builder(builderMethodName = "newCustomSupplyItemBuilder", builderClassName = "NewCustomSupplyItemBuilder")
    public CustomSupplyItem(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    private CustomSupplyItem() {
    }
}
