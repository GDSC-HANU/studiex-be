package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
public class DemandsDTO {
    public Id studierId;
    public List<DemandDTO> demands;

    @Builder
    public static class DemandDTO {
        public String subjectName;
        public List<DemandItemDTO> demandItems;
        public boolean active;
        public DemandItemPriority priority;
        public List<CustomDemandItem> customDemandItems;
    }

    @Builder
    public static class DemandItemDTO {
        public String key;
        public DemandItemOperator operator;
        public DemandItemValueDTO value;
        public String description;
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
}
