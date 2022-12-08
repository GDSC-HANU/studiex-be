package com.gdsc.studyex.domain.supply.models;

import java.util.List;

public class AllowedSupplyItemArrayValue extends AllowedSupplyItemValue{
    private List<String> values;

    private AllowedSupplyItemArrayValue(List<String> values) {
        this.values = values;
    }

    public static AllowedSupplyItemArrayValue create(List<String> values) {
        return new AllowedSupplyItemArrayValue(values);
    }
}
