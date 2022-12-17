package com.gdsc.studyex.domain.supply_and_demand.models.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemRangeValue;
import lombok.Getter;

@Getter
public class SupplyItemRangeValue implements SupplyItemValue {
    private double minValue;
    private double maxValue;

    public void validate(AllowedSupplyItemRangeValue allowedSupplyItemRangeValue) throws InvalidInputException {
        if (getMinValue() < allowedSupplyItemRangeValue.getMinValue())
            throw new InvalidInputException("Invalid Supply Item Value Min Value, require smaller than " + minValue + ": " + getMinValue());
        if (allowedSupplyItemRangeValue.getMaxValue() < getMaxValue())
            throw new InvalidInputException("Invalid Supply Item Value Max Value, require greater than " + maxValue + ": " + getMaxValue());
        if ((getMinValue() - allowedSupplyItemRangeValue.getMinValue()) % allowedSupplyItemRangeValue.getDifferent() != 0)
            throw new InvalidInputException("Invalid Supply Item Value Min Value, require different to be power of "
                    + allowedSupplyItemRangeValue.getDifferent() + ": " + getMinValue());
        if ((allowedSupplyItemRangeValue.getMaxValue() - getMaxValue()) % allowedSupplyItemRangeValue.getDifferent() != 0)
            throw new InvalidInputException("Invalid Supply Item Value Max Value, require different to be power of "
                    + allowedSupplyItemRangeValue.getDifferent() + ": " + getMaxValue());
    }
}
