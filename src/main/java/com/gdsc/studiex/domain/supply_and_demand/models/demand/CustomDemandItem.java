package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import lombok.Builder;

public class CustomDemandItem {
    private String key;
    private String value;
    private String description;

    @Builder(builderMethodName = "newCustomDemandItemBuilder", builderClassName = "NewCustomDemandItemBuilder")
    public CustomDemandItem(String key, String value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }
}
