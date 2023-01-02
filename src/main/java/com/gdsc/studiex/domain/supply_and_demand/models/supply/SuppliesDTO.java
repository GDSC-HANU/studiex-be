package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemArrayValueElement;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.CustomSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyPriority;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class SuppliesDTO {
    public Id studierId;
    public List<SupplyDTO> supplies;

    @Builder
    public static class SupplyDTO {
        public String subjectName;
        public List<SupplyItemDTO> supplyItems;
        public boolean active;
        public SupplyPriority priority;
        public List<CustomSupplyItem> customSupplyItems;
    }

    @Builder
    public static class SupplyItemDTO {
        public String key;
        public SupplyItemOperator operator;
        public SupplyItemValueDTO value;
        public String description;

        public static SupplyItemValueDTO buildSupplyItemValueDTO(SupplyItemOperator operator,
                                                                 SupplyItemValue supplyItemValue,
                                                                 AllowedSupplyItemValue allowedSupplyItemValue) {
            final AllowedSupplyItemArrayValue allowedSupplyItemArrayValue = CustomObjectMapper.convertObjectClass(allowedSupplyItemValue, AllowedSupplyItemArrayValue.class);
            switch (operator) {
                case IS -> {
                    final SupplyItemExactValue supplyItemExactValue = CustomObjectMapper.convertObjectClass(supplyItemValue, SupplyItemExactValue.class);
                    for (AllowedSupplyItemArrayValueElement element : allowedSupplyItemArrayValue.getElements()) {
                        if (supplyItemExactValue.getAllowedSupplyItemArrayValueId().equals(element.getId())) {
                            return SupplyItemExactValueDTO.newSupplyItemExactValueDTO()
                                    .value(element.getValue())
                                    .build();
                        }
                    }
                    return null;
                }
                case ARE -> {
                    final SupplyItemArrayValue supplyItemArrayValue = CustomObjectMapper.convertObjectClass(supplyItemValue, SupplyItemArrayValue.class);
                    Map<Id, String> allowedSupplyItemValueElementMap = allowedSupplyItemArrayValue.getElements().stream()
                            .collect(Collectors.toMap(
                                    AllowedSupplyItemArrayValueElement::getId,
                                    AllowedSupplyItemArrayValueElement::getValue));
                    return SupplyItemArrayValueDTO.newSupplyItemArrayValueDTO()
                            .value(supplyItemArrayValue.getAllowedSupplyItemArrayValueIds().stream()
                                    .map(allowedSupplyItemValueElementMap::get)
                                    .collect(Collectors.toList()))
                            .build();
                }
                case BETWEEN -> {
                    final SupplyItemRangeValue supplyItemRangeValue = CustomObjectMapper.convertObjectClass(supplyItemValue, SupplyItemRangeValue.class);
                    return SupplyItemRangeValueDTO.newSupplyItemRangeValueDTO()
                            .minValue(supplyItemRangeValue.getMinValue())
                            .maxValue(supplyItemRangeValue.getMaxValue())
                            .build();
                }
                default -> throw new InvalidInputException("Invalid Supply Item Value");
            }
        }
    }

    public static interface SupplyItemValueDTO {
    }

    public static class SupplyItemArrayValueDTO extends ArrayList<String> implements SupplyItemValueDTO {
        @Builder(builderMethodName = "newSupplyItemArrayValueDTO", builderClassName = "NewSupplyItemArrayValueDTO")
        public SupplyItemArrayValueDTO(List<String> value) {
            super(value);
        }
    }

    public static class SupplyItemExactValueDTO extends StringValueObject implements SupplyItemValueDTO {
        @Builder(builderMethodName = "newSupplyItemExactValueDTO", builderClassName = "NewSupplyItemExactValueDTO")
        public SupplyItemExactValueDTO(String value) throws InvalidInputException {
            super(value);
        }

        
    }

    public static class SupplyItemRangeValueDTO  implements SupplyItemValueDTO {
        public double minValue;
        public double maxValue;

        @Builder(builderMethodName = "newSupplyItemRangeValueDTO", builderClassName = "NewSupplyItemRangeValueDTO")
        public SupplyItemRangeValueDTO(double minValue, double maxValue) {
            this.minValue = minValue;
            this.maxValue = maxValue;
        }
    }
}
