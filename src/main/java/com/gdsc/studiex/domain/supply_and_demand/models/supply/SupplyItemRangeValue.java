package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SupplyItemRangeValue implements SupplyItemValue {
    private double minValue;
    private double maxValue;
    @Builder(builderMethodName = "newSupplyItemRangeValue", builderClassName = "NewSupplyItemRangeValue")
    public SupplyItemRangeValue(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    private SupplyItemRangeValue() {
    }

    public void validate(AllowedSupplyItemRangeValue allowedSupplyItemRangeValue) throws InvalidInputException {
        if (getMinValue() < allowedSupplyItemRangeValue.getMinValue())
            throw new InvalidInputException("Invalid Supply Item Value Min Value, require smaller than " + minValue + ": " + getMinValue());
        if (allowedSupplyItemRangeValue.getMaxValue() < getMaxValue())
            throw new InvalidInputException("Invalid Supply Item Value Max Value, require greater than " + maxValue + ": " + getMaxValue());
        if ((getMinValue() - allowedSupplyItemRangeValue.getMinValue()) % allowedSupplyItemRangeValue.getDifference() != 0)
            throw new InvalidInputException("Invalid Supply Item Value Min Value, require different to be power of "
                    + allowedSupplyItemRangeValue.getDifference() + ": " + getMinValue());
        if ((allowedSupplyItemRangeValue.getMaxValue() - getMaxValue()) % allowedSupplyItemRangeValue.getDifference() != 0)
            throw new InvalidInputException("Invalid Supply Item Value Max Value, require different to be power of "
                    + allowedSupplyItemRangeValue.getDifference() + ": " + getMaxValue());
    }

    @Override
    public SuppliesDTO.SupplyItemValueDTO buildSupplyItemValueDTO(AllowedSupplyItemValue allowedSupplyItemValue) {
        return SuppliesDTO.SupplyItemRangeValueDTO.builder()
                .minValue(minValue)
                .maxValue(maxValue)
                .build();
    }

    @Override
    public boolean canBeUsedWith(SupplyItemOperator supplyItemOperator) {
        return supplyItemOperator.equals(SupplyItemOperator.BETWEEN);
    }
}
