package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemRangeValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AllowedSupplyItemRangeValue implements AllowedSupplyItemValue {
    private double minValue;
    private double maxValue;
    private double difference;

    @Builder(builderMethodName = "newAllowedSupplyItemRangeValue", builderClassName = "NewAllowedSupplyItemRangeValue")
    public AllowedSupplyItemRangeValue(double minValue, double maxValue, double difference) throws InvalidInputException {
        if (minValue % difference != 0 || maxValue % difference != 0) {
            throw new InvalidInputException("Invalid difference");
        }
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.difference = difference;
    }

    private AllowedSupplyItemRangeValue() {

    }

    @Override
    public SupplyItemValue toSupplyItemValue(SupplyItemOperator supplyItemOperator, SuppliesDTO.SupplyItemValueDTO supplyItemValue) throws InvalidInputException {
        if (!supplyItemOperator.equals(SupplyItemOperator.BETWEEN)) {
            throw new InvalidInputException("Invalid Supply Item Operator, require BETWEEN: " + supplyItemOperator);
        }
        try {
            final SuppliesDTO.SupplyItemRangeValueDTO value = CustomObjectMapper.convertObjectClass(supplyItemValue, SuppliesDTO.SupplyItemRangeValueDTO.class);
            final SupplyItemRangeValue rangeValue = SupplyItemRangeValue.newSupplyItemRangeValue()
                    .minValue(value.minValue)
                    .maxValue(value.maxValue)
                    .build();
            rangeValue.validate(this);
            return rangeValue;
        } catch (Throwable e) {
            throw new InvalidInputException("Invalid Supply Item Value, require String: " + supplyItemValue);
        }
    }

    @Override
    public AllowedSupplyDTO.AllowedSupplyItemValueDTO toAllowedSupplyItemValueDTO() {
        return AllowedSupplyDTO.AllowedSupplyRangeValueDTO.builder()
                .minValue(minValue)
                .maxValue(maxValue)
                .difference(difference)
                .build();
    }

    @Override
    public boolean canBeUsedWith(AllowedSupplyOperator operator) {
        return operator.equals(AllowedSupplyOperator.BETWEEN);
    }
}
