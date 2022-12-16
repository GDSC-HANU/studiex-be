package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import lombok.Getter;

@Getter
public class SupplyItemRangeValue implements SupplyItemValue {
    private double minValue;
    private double maxValue;
}
