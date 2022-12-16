package com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemRangeValue;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studyex.infrastructure.share.objectMapper.CustomObjectMapper;
import lombok.Builder;

public class AllowedSupplyItemRangeValue implements AllowedSupplyItemValue {
    private double minValue;
    private double maxValue;
    private double different;

    @Builder(builderMethodName = "newAllowedSupplyItemRangeValue", builderClassName = "NewAllowedSupplyItemRangeValue")
    public AllowedSupplyItemRangeValue(double minValue, double maxValue, double different) throws InvalidInputException {
        if (minValue % different != 0 || maxValue % different != 0) {
            throw new InvalidInputException("Invalid different");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.different = different;
    }

    @Override
    public SupplyItemValue convertToSupplyItemValue(SupplyItemOperator supplyItemOperator, Object supplyItemValue) throws InvalidInputException {
        if (!supplyItemOperator.equals(SupplyItemOperator.BETWEEN)) {
            throw new InvalidInputException("Invalid Supply Item Operator, require BETWEEN: " + supplyItemOperator);
        }
        try {
            final SupplyItemRangeValue value = CustomObjectMapper.convertObjectClass(supplyItemValue, SupplyItemRangeValue.class);
            if (value.getMinValue() < minValue)
                throw new InvalidInputException("Invalid Supply Item Value Min Value, require smaller than " + minValue + ":" + value.getMinValue());
            if (maxValue < value.getMaxValue())
                throw new InvalidInputException("Invalid Supply Item Value Min Value, require greater than " + maxValue + ":" + value.getMaxValue());
        } catch (Throwable e) {
            throw new InvalidInputException("Invalid Supply Item Value, require String: " + supplyItemValue);
        }
    }
}
