package com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.infrastructure.share.objectMapper.CustomObjectMapper;
import lombok.Builder;

public class AllowedSupplyItem {
    private String key;
    private AllowedSupplyOperator operator;
    private Object value;
    private String description;

    @Builder(builderMethodName = "newAllowedSupplyItemBuilder", builderClassName = "NewAllowedSupplyItemBuilder")
    public AllowedSupplyItem(String key, AllowedSupplyOperator operator, Object value, String description) throws InvalidInputException {
        this.key = key;
        this.operator = operator;
        this.value = value;
        this.description = description;
        validate();
    }

    private void validate() throws InvalidInputException {
        if (key == null || key.trim().isEmpty())
            throw new InvalidInputException("AllowedSupplyItem.key must not be null");
        if (operator == null)
            throw new InvalidInputException("AllowedSupplyItem.operator must not be null");
        validateValue();
    }

    private void validateValue() throws InvalidInputException {
        if (value == null)
            throw new InvalidInputException("AllowedSupplyItem.value must not be null");
        switch (operator) {
            case ONE_OF:
            case MANY_OF:
                try {
                    value = CustomObjectMapper.convertObjectClass(
                            value,
                            AllowedSupplyItemArrayValue.class
                    );
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid AllowedSupplyItem.value for operator " + operator);
                }
                break;
            case BETWEEN:
                try {
                    value = CustomObjectMapper.convertObjectClass(
                            value,
                            AllowedSupplyItemRangeValue.class
                    );
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid AllowedSupplyItem.value for operator " + operator);
                }
                break;
        }
    }
}
