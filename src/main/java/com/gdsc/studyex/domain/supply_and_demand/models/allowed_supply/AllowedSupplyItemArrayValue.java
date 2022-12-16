package com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemExactValue;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studyex.infrastructure.share.objectMapper.CustomObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class AllowedSupplyItemArrayValue extends ArrayList<String> implements AllowedSupplyItemValue {
    @Override
    public SupplyItemValue convertToSupplyItemValue(SupplyItemOperator supplyItemOperator, Object supplyItemValue) throws InvalidInputException {
        switch (supplyItemOperator) {
            case IS:
                try {
                    final String value = CustomObjectMapper.convertObjectClass(supplyItemValue, String.class);
                    for (int i = 0; i < size(); i++)
                        if (get(i).equals(value))
                            return SupplyItemExactValue.newSupplyItemExactValue()
                                    .allowedSupplyItemArrayValueIndex(i)
                                    .build();
                    throw new InvalidInputException("Unknown Supply Item Value: " + value);
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Supply Item Value, require String: " + supplyItemValue);
                }
            case ARE:
                try {
                    final List<String> values = CustomObjectMapper.convertObjectClass(supplyItemValue, List.class);
                    final List<Integer> indexes = new ArrayList<>();
                    for (String value : values) {
                        boolean found = false;
                        for (int i = 0; i < size(); i++)
                            if (get(i).equals(value)) {
                                indexes.add(i);
                                found = true;
                            }
                        if (!found)
                            throw new InvalidInputException("Unknown Supply Item Value: " + value);
                    }
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Supply Item Value, require List<String>: " + supplyItemValue);
                }
            default:
                throw new InvalidInputException("Invalid Supply Item Operator, require IS, ARE: " + supplyItemOperator);
        }
    }
}
