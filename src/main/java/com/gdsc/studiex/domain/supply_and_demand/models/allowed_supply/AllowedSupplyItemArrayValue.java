package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.*;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AllowedSupplyItemArrayValue implements AllowedSupplyItemValue {
    private List<AllowedSupplyItemArrayValueElement> elements;

    @Builder(builderMethodName = "newAllowedSupplyItemArrayValue", builderClassName = "NewAllowedSupplyItemArrayValue")
    public AllowedSupplyItemArrayValue(List<AllowedSupplyItemArrayValueElement> elements) {
        if (elements == null)
            elements = new ArrayList<>();
        this.elements = elements;
    }

    private AllowedSupplyItemArrayValue() {
    }

    public static AllowedSupplyItemArrayValue fromListString(List<String> values) {
        final List<AllowedSupplyItemArrayValueElement> elements = values.stream()
                .map(value -> AllowedSupplyItemArrayValueElement.newAllowedSupplyItemArrayValueElement()
                        .value(value)
                        .build())
                .collect(Collectors.toList());
        return AllowedSupplyItemArrayValue.newAllowedSupplyItemArrayValue()
                .elements(elements)
                .build();
    }

    @Override
    public SupplyItemValue toSupplyItemValue(SupplyItemOperator supplyItemOperator, SuppliesDTO.SupplyItemValueDTO supplyItemValue) throws InvalidInputException {
        switch (supplyItemOperator) {
            case IS:
                try {
                    final SuppliesDTO.SupplyItemExactValueDTO value = CustomObjectMapper.convertObjectClass(supplyItemValue, SuppliesDTO.SupplyItemExactValueDTO.class);
                    for (AllowedSupplyItemArrayValueElement element : elements)
                        if (element.getValue().equals(value.toString()))
                            return SupplyItemExactValue.newSupplyItemExactValue()
                                    .allowedSupplyItemArrayValueId(element.getId())
                                    .build();
                    throw new InvalidInputException("Unknown Supply Item Value: " + value);
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Supply Item Value, require String: " + supplyItemValue);
                }
            case ARE:
                try {
                    final SuppliesDTO.SupplyItemArrayValueDTO values = CustomObjectMapper.convertObjectClass(supplyItemValue, SuppliesDTO.SupplyItemArrayValueDTO.class);
                    final List<Id> ids = new ArrayList<>();
                    for (String value : values) {
                        boolean found = false;
                        for (AllowedSupplyItemArrayValueElement element : elements)
                            if (element.getValue().equals(value)) {
                                ids.add(element.getId());
                                found = true;
                            }
                        if (!found)
                            throw new InvalidInputException("Unknown Supply Item Value: " + value);
                    }
                    return SupplyItemArrayValue.newSupplyItemArrayValue()
                            .allowedSupplyItemArrayValueIds(ids)
                            .build();
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Supply Item Value, require List<String>: " + supplyItemValue);
                }
            default:
                throw new InvalidInputException("Invalid Supply Item Operator, require IS, ARE: " + supplyItemOperator);
        }
    }

    @Override
    public AllowedSupplyDTO.AllowedSupplyItemValueDTO toAllowedSupplyItemValueDTO() {
        final List<String> values = elements.stream()
                .map(e -> e.getValue())
                .collect(Collectors.toList());
        return new AllowedSupplyDTO.AllowedSupplyArrayValueDTO(values);
    }

    @Override
    public boolean canBeUsedWith(AllowedSupplyOperator operator) {
        return operator.equals(AllowedSupplyOperator.ONE_OF) || operator.equals(AllowedSupplyOperator.MANY_OF);
    }
}
