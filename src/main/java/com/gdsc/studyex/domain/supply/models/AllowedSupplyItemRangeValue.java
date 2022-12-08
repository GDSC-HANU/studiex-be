package com.gdsc.studyex.domain.supply.models;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;

public class AllowedSupplyItemRangeValue extends AllowedSupplyItemValue {
    private double minValue;
    private double maxValue;
    private double different;

    private AllowedSupplyItemRangeValue(double minValue, double maxValue, double different) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.different = different;
    }

    public AllowedSupplyItemRangeValue createRangeValue(double minValue, double maxValue, double different) throws InvalidInputException {
        if (minValue % different != 0 || maxValue % different != 0) {
            throw new InvalidInputException("Invalid different");
        }
        return new AllowedSupplyItemRangeValue(minValue, maxValue, different);
    }
}
