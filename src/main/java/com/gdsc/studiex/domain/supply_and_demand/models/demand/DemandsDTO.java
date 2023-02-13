package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import lombok.Builder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public class DemandsDTO {
    public Id studierId;
    public List<DemandDTO> demands;

    @Builder
    public static class DemandDTO {
        public String subjectName;
        public List<DemandItemDTO> demandItems;
        public boolean active;
        public DemandPriority priority;
        public List<CustomDemandItem> customDemandItems;

        public static DemandsDTO.DemandDTO fromDemand(Demand demand, AllowedSupply allowedSupply) {
            Map<DemandItem, AllowedSupplyItem> demandItemMap = demand.getDemandItems().stream()
                    .collect(Collectors.toMap(
                            demandItem -> demandItem,
                            demandItem -> allowedSupply.findItemById(demandItem.getAllowedSupplyItemId())));
            List<DemandsDTO.DemandItemDTO> demandItemDTOS = new LinkedList<>();
            for (Map.Entry<DemandItem, AllowedSupplyItem> entry : demandItemMap.entrySet()) {
                demandItemDTOS.add(DemandItemDTO.fromDemandItem(entry.getKey(), entry.getValue()));
            }
            return DemandsDTO.DemandDTO.builder()
                    .subjectName(allowedSupply.getSubjectName())
                    .demandItems(demandItemDTOS)
                    .active(demand.isActive())
                    .priority(demand.getPriority())
                    .customDemandItems(demand.getCustomDemandItems())
                    .build();
        }
    }

    @Builder
    public static class DemandItemDTO {
        public String key;
        public DemandItemOperator operator;
        public DemandItemValueDTO value;
        public String description;
        public boolean required;

        public static DemandItemDTO fromDemandItem(DemandItem demandItem, AllowedSupplyItem allowedSupplyItem) {
            return DemandsDTO.DemandItemDTO.builder()
                    .key(allowedSupplyItem.getKey())
                    .value(demandItem.getValue().buildeDemandItemValueDto(allowedSupplyItem.getValue()))
                    .operator(demandItem.getOperator())
                    .description(demandItem.getDescription())
                    .required(demandItem.isRequired())
                    .build();
        }
    }

    public static interface DemandItemValueDTO {
    }

    public static class DemandItemArrayValueDTO extends ArrayList<String> implements DemandItemValueDTO {
        public DemandItemArrayValueDTO(List<String> value) {
            super(value);
        }

        public DemandItemArrayValueDTO() {
        }
    }

    public static class DemandItemExactValueDTO extends StringValueObject implements DemandItemValueDTO {
        public DemandItemExactValueDTO(String value) throws InvalidInputException {
            super(value);
        }
    }

    @Builder
    public static class DemandItemRangeValueDTO implements DemandItemValueDTO {
        public double minValue;
        public double maxValue;
    }

    public static DemandsDTO fromDemands(Demands demands, List<AllowedSupply> allowedSupplies) {
        Map<String, AllowedSupply> allowedSupplyMap = allowedSupplies.stream()
                .collect(Collectors.toMap(allowedSupply -> allowedSupply.getId().toString(), allowedSupply -> allowedSupply));
        Map<Demand, AllowedSupply> demandMap = demands.getDemands().stream()
                .collect(Collectors.toMap(
                        demand -> demand,
                        demand -> {
                            Id allowedSupplyId = demand.getAllowedSupplyId();
                            AllowedSupply allowedSupply = allowedSupplyMap.get(allowedSupplyId.toString());
                            return allowedSupply;
                        }
                ));
        List<DemandsDTO.DemandDTO> demandDTOS = new LinkedList<>();
        for (Map.Entry<Demand, AllowedSupply> entry : demandMap.entrySet()) {
            demandDTOS.add(DemandDTO.fromDemand(entry.getKey(), entry.getValue()));
        }
        return DemandsDTO.builder()
                .studierId(demands.getStudierId())
                .demands(demandDTOS)
                .build();
    }
}
