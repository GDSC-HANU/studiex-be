package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.*;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.*;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
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
    public SupplyItemValue toSupplyItemValue(AllowedSupplyOperator allowedSupplyOperator,
                                             SupplyItemOperator supplyItemOperator,
                                             SuppliesDTO.SupplyItemValueDTO supplyItemValueDTO) throws InvalidInputException {
        switch (supplyItemOperator) {
            case IS:
                if (!allowedSupplyOperator.equals(AllowedSupplyOperator.ONE_OF))
                    throw new InvalidInputException("Invalid Supply Item Operator: " + supplyItemOperator);
                try {
                    final SuppliesDTO.SupplyItemExactValueDTO value = CustomObjectMapper.convertObjectClass(supplyItemValueDTO, SuppliesDTO.SupplyItemExactValueDTO.class);
                    for (AllowedSupplyItemArrayValueElement element : elements)
                        if (element.getValue().equals(value.toString()))
                            return SupplyItemExactValue.newSupplyItemExactValueBuilder()
                                    .allowedSupplyItemArrayValueId(element.getId())
                                    .build();
                    throw new InvalidInputException("Unknown Supply Item Value: " + value);
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Supply Item Value, require String: " + supplyItemValueDTO);
                }
            case ARE:
                if (!allowedSupplyOperator.equals(AllowedSupplyOperator.MANY_OF))
                    throw new InvalidInputException("Invalid Supply Item Operator: " + supplyItemOperator);
                try {
                    final SuppliesDTO.SupplyItemArrayValueDTO values = CustomObjectMapper.convertObjectClass(supplyItemValueDTO, SuppliesDTO.SupplyItemArrayValueDTO.class);
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
                    return SupplyItemArrayValue.newSupplyItemArrayValueBuilder()
                            .allowedSupplyItemArrayValueIds(ids)
                            .build();
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Supply Item Value, require List<String>: " + supplyItemValueDTO);
                }
            default:
                throw new InvalidInputException("Invalid Supply Item Operator, require IS, ARE: " + supplyItemOperator);
        }
    }

    @Override
    public DemandItemValue toDemandItemValue(AllowedSupplyOperator allowedSupplyOperator,
                                             DemandItemOperator demandItemOperator,
                                             DemandsDTO.DemandItemValueDTO demandItemValueDTO) throws InvalidInputException {
        switch (demandItemOperator) {
            case IS:
                if (!allowedSupplyOperator.equals(AllowedSupplyOperator.ONE_OF))
                    throw new InvalidInputException("Invalid Demand Item Operator: " + demandItemOperator);
                try {
                    final DemandsDTO.DemandItemExactValueDTO value = CustomObjectMapper.convertObjectClass(demandItemValueDTO, DemandsDTO.DemandItemExactValueDTO.class);
                    for (AllowedSupplyItemArrayValueElement element : elements)
                        if (element.getValue().equals(value.toString()))
                            return DemandItemExactValue.newDemandItemExactValueBuilder()
                                    .allowedSupplyItemArrayValueId(element.getId())
                                    .build();
                    throw new InvalidInputException("Unknown Demand Item Value: " + value);
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Demand Item Value, require String: " + demandItemValueDTO);
                }
            case INCLUDES_ANY:
                if (!allowedSupplyOperator.equals(AllowedSupplyOperator.MANY_OF))
                    throw new InvalidInputException("Invalid Demand Item Operator: " + demandItemOperator);
                try {
                    final DemandsDTO.DemandItemArrayValueDTO values = CustomObjectMapper.convertObjectClass(demandItemValueDTO, DemandsDTO.DemandItemArrayValueDTO.class);
                    final List<Id> ids = new ArrayList<>();
                    for (String value : values) {
                        boolean found = false;
                        for (AllowedSupplyItemArrayValueElement element : elements)
                            if (element.getValue().equals(value)) {
                                ids.add(element.getId());
                                found = true;
                            }
                        if (!found)
                            throw new InvalidInputException("Unknown Demand Item Value: " + value);
                    }
                    return DemandItemArrayValue.newDemandItemArrayValueBuilder()
                            .allowedSupplyItemArrayValueIds(ids)
                            .build();
                } catch (Throwable e) {
                    throw new InvalidInputException("Invalid Demand Item Value, require List<String>: " + demandItemValueDTO);
                }
            case INCLUDES_ALL:
                if (!allowedSupplyOperator.equals(AllowedSupplyOperator.MANY_OF))
                    throw new InvalidInputException("Invalid Demand Item Operator: " + demandItemOperator);
                return DemandItemArrayValue.newDemandItemArrayValueBuilder()
                        .allowedSupplyItemArrayValueIds(elements.stream()
                                .map(element -> element.getId())
                                .collect(Collectors.toList()))
                        .build();
        }
        throw new InvalidInputException("Invalid Demand Item Operator, require IS, INCLUDES_ANY, INCLUDES_ALL: " + demandItemOperator);
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
