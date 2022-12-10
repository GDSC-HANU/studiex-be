package com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import lombok.Builder;

public class AllowedSupplyItemRangeValue extends AllowedSupplyItemValue {
    private double minValue;
    private double maxValue;
    private double different;

    @Builder(builderMethodName = "newAllowedSupplyItemRangeValue", builderClassName = "NewAllowedSupplyItemRangeValue")
    private AllowedSupplyItemRangeValue(double minValue, double maxValue, double different) throws InvalidInputException {
        if (minValue % different != 0 || maxValue % different != 0) {
            throw new InvalidInputException("Invalid different");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.different = different;
    }
}
