package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemRangeValue;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DemandItemRangeValue implements DemandItemValue {
    private double minValue;
    private double maxValue;
    @Builder(builderMethodName = "newDemandItemRangeValue", builderClassName = "NewDemandItemRangeValue")
    public DemandItemRangeValue(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private DemandItemRangeValue() {}

    public void validate(AllowedSupplyItemRangeValue allowedSupplyItemRangeValue) throws InvalidInputException {
        if (getMinValue() < allowedSupplyItemRangeValue.getMinValue())
            throw new InvalidInputException("Invalid Demand Item Value Min Value, require smaller than " + minValue + ": " + getMinValue());
        if (allowedSupplyItemRangeValue.getMaxValue() < getMaxValue())
            throw new InvalidInputException("Invalid Demand Item Value Max Value, require greater than " + maxValue + ": " + getMaxValue());
        if ((getMinValue() - allowedSupplyItemRangeValue.getMinValue()) % allowedSupplyItemRangeValue.getDifference() != 0)
            throw new InvalidInputException("Invalid Demand Item Value Min Value, require different to be power of "
                    + allowedSupplyItemRangeValue.getDifference() + ": " + getMinValue());
        if ((allowedSupplyItemRangeValue.getMaxValue() - getMaxValue()) % allowedSupplyItemRangeValue.getDifference() != 0)
            throw new InvalidInputException("Invalid Demand Item Value Max Value, require different to be power of "
                    + allowedSupplyItemRangeValue.getDifference() + ": " + getMaxValue());
    }

    @Override
    public DemandsDTO.DemandItemValueDTO buildeDemandItemValueDto(AllowedSupplyItemValue allowedSupplyItemValue) {
        return DemandsDTO.DemandItemRangeValueDTO.builder()
                .minValue(minValue)
                .maxValue(maxValue)
                .build();
    }

    @Override
    public boolean match(DemandItemOperator operator, SupplyItem supplyItem) {
        final SupplyItemRangeValue supplyItemArrayValue = (SupplyItemRangeValue) supplyItem.getValue();
        return minValue <= supplyItemArrayValue.getMinValue()
                && supplyItemArrayValue.getMaxValue() <= maxValue;
    }

    @Override
    public int totalCriteria(DemandItemOperator operator) {
        return 1;
    }

    @Override
    public int matchCriteria(DemandItemOperator operator, SupplyItem supplyItem) {
        if (!match(operator, supplyItem))
            return 0;
        return 1;
    }

    @Override
    public boolean canBeUsedWith(DemandItemOperator operator) {
        return operator.equals(DemandItemOperator.BETWEEN);
    }
}
