package com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemExactValue;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studyex.infrastructure.share.objectMapper.CustomObjectMapper;

import java.util.ArrayList;

public class AllowedSupplyItemArrayValue extends ArrayList<String> implements AllowedSupplyItemValue {
    @Override
    public SupplyItemValue convertToSupplyItemValue(SupplyItemOperator supplyItemOperator, Object supplyItemValue) throws InvalidInputException {
        switch (supplyItemOperator) {
            case IS:
                try {
                    final String value = CustomObjectMapper.convertObjectClass(supplyItemValue, String.class);
                    for (int i = 0; i < size(); i++)
                        if (get(i).equals(supplyItemValue))
                            return SupplyItemExactValue.newSupplyItemExactValue()
                                    .allowedSupplyItemArrayValueIndex(i)
                                    .build();
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid supplyItemValue, require String: " + supplyItemValue);
                }
            case ARE:
                try {

                } catch (Throwable e) {

                }

        }
        return null;
    }
}
