package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;

public class AllowedSupplyItemValueFactory {
    public static AllowedSupplyItemValue get(AllowedSupplyOperator operator, Object allowedSupplyItemValue) throws InvalidInputException {
        switch (operator) {
            case ONE_OF:
            case MANY_OF:
                try {
                    return CustomObjectMapper.convertObjectClass(
                            allowedSupplyItemValue,
                            AllowedSupplyItemArrayValue.class
                    );
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid AllowedSupplyItem.value for operator " + operator);
                }
            case BETWEEN:
                try {
                    return CustomObjectMapper.convertObjectClass(
                            allowedSupplyItemValue,
                            AllowedSupplyItemRangeValue.class
                    );
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid AllowedSupplyItem.value for operator " + operator);
                }
        }
        return null;
    }
}
