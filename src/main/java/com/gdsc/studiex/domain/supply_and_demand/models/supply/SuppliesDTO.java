package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuppliesDTO {
    public Id studierId;
    public List<SupplyDTO> supplies;



    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplyDTO {
        @NotNull
        public String subjectName;
        @NotNull
        public List<SupplyItemDTO> supplyItems;
        public boolean active;
        @NotNull
        public SupplyPriority priority;
        public List<CustomSupplyItem> customSupplyItems;

        public static SupplyDTO fromSupply(Supply supply, AllowedSupply allowedSupply) {
            Map<SupplyItem, AllowedSupplyItem> supplyItemMap = supply.getSupplyItems().stream()
                    .collect(Collectors.toMap(
                            supplyItem -> supplyItem,
                            supplyItem -> allowedSupply.findItemById(supplyItem.getAllowedSupplyItemId())));
            List<SuppliesDTO.SupplyItemDTO> supplyItemDTOS = new LinkedList<>();
            for(Map.Entry<SupplyItem, AllowedSupplyItem> entry : supplyItemMap.entrySet()) {
                supplyItemDTOS.add(SupplyItemDTO.fromSupplyItem(entry.getKey(), entry.getValue()));
            }
            return SuppliesDTO.SupplyDTO.builder()
                    .subjectName(allowedSupply.getSubjectName())
                    .supplyItems(supplyItemDTOS)
                    .active(supply.isActive())
                    .priority(supply.getPriority())
                    .customSupplyItems(supply.getCustomSupplyItems())
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplyItemDTO {
        public String key;
        public SupplyItemOperator operator;
        public SupplyItemValueDTO value;
        public String description;

        public static SupplyItemDTO fromSupplyItem(SupplyItem supplyItem, AllowedSupplyItem allowedSupplyItem) {
            return SuppliesDTO.SupplyItemDTO.builder()
                    .key(allowedSupplyItem.getKey())
                    .value(supplyItem.getValue().buildSupplyItemValueDTO(allowedSupplyItem.getValue()))
                    .operator(supplyItem.getOperator())
                    .description(supplyItem.getDescription())
                    .build();
        }
    }

    public static interface SupplyItemValueDTO {
    }

    public static class SupplyItemArrayValueDTO extends ArrayList<String> implements SupplyItemValueDTO {
        public SupplyItemArrayValueDTO(List<String> value) {
            super(value);
        }

        public SupplyItemArrayValueDTO() {
        }
    }

    public static class SupplyItemExactValueDTO extends StringValueObject implements SupplyItemValueDTO {
        public SupplyItemExactValueDTO(String value) throws InvalidInputException {
            super(value);
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupplyItemRangeValueDTO  implements SupplyItemValueDTO {
        public double minValue;
        public double maxValue;
    }

    public static SuppliesDTO fromSupplies(Supplies supplies, List<AllowedSupply> allowedSupplies) {
        Map<String, AllowedSupply> allowedSupplyMap = allowedSupplies.stream()
                .collect(Collectors.toMap(allowedSupply -> allowedSupply.getId().toString(), allowedSupply -> allowedSupply));
        Map<Supply, AllowedSupply> supplyMap = supplies.getSupplies().stream()
                .collect(Collectors.toMap(
                        supply -> supply,
                        supply -> {
                            Id allowedSupplyId = supply.getAllowedSupplyId();
                            AllowedSupply allowedSupply = allowedSupplyMap.get(allowedSupplyId.toString());
                            return allowedSupply;
                        }
                ));
        List<SuppliesDTO.SupplyDTO> supplyDTOs = new LinkedList<>();
        for(Map.Entry<Supply, AllowedSupply> entry : supplyMap.entrySet()) {
            supplyDTOs.add(SupplyDTO.fromSupply(entry.getKey(), entry.getValue()));
        }
        return SuppliesDTO.builder()
                .studierId(supplies.getStudierId())
                .supplies(supplyDTOs)
                .build();
    }
}
