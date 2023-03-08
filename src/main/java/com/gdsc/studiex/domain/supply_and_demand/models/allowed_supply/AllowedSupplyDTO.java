package com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class AllowedSupplyDTO {
    public Id id;
    public String subjectName;
    public List<AllowedSupplyItemDTO> allowedSupplyItems;

    public static AllowedSupplyDTO fromAllowedSupply(AllowedSupply allowedSupply) {
        return AllowedSupplyDTO.builder()
                .id(allowedSupply.getId())
                .subjectName(allowedSupply.getSubjectName())
                .allowedSupplyItems(allowedSupply.getAllowedSupplyItems()
                        .stream()
                        .map(allowedSupplyItem -> AllowedSupplyItemDTO.fromAllowedSupplyItem(allowedSupplyItem))
                        .collect(Collectors.toList()))
                .build();
    }

    @Builder
    @Getter
    public static class AllowedSupplyItemDTO {
        public Id id;
        public String key;
        public AllowedSupplyOperator operator;
        public AllowedSupplyItemValueDTO value;
        public String description;
        public boolean required;

        public static AllowedSupplyItemDTO fromAllowedSupplyItem(AllowedSupplyItem allowedSupplyItem) {
            return AllowedSupplyItemDTO.builder()
                    .id(allowedSupplyItem.getId())
                    .key(allowedSupplyItem.getKey())
                    .operator(allowedSupplyItem.getOperator())
                    .value(allowedSupplyItem.getValue().toAllowedSupplyItemValueDTO())
                    .description(allowedSupplyItem.getDescription())
                    .required(allowedSupplyItem.isRequired())
                    .build();
        }
    }

    public static interface AllowedSupplyItemValueDTO {
        public AllowedSupplyItemValue toAllowedSupplyItem();
    }

    @Builder
    public static class AllowedSupplyRangeValueDTO implements AllowedSupplyItemValueDTO {
        public double minValue;
        public double maxValue;
        public double difference;

        @Override
        public AllowedSupplyItemValue toAllowedSupplyItem() {
            return AllowedSupplyItemRangeValue.newAllowedSupplyItemRangeValue()
                    .difference(difference)
                    .maxValue(maxValue)
                    .minValue(minValue)
                    .build();
        }
    }

    public static class AllowedSupplyArrayValueDTO extends ArrayList<String> implements AllowedSupplyItemValueDTO {
        public AllowedSupplyArrayValueDTO() {
        }

        public AllowedSupplyArrayValueDTO(Collection<? extends String> c) {
            super(c);
        }

        @Override
        public AllowedSupplyItemValue toAllowedSupplyItem() {
            return AllowedSupplyItemArrayValue.fromListString(this);
        }
    }
}
