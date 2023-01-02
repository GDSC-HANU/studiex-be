package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;

import java.util.ArrayList;
import java.util.List;

public class DemandsDTO {
    public Id studierId;
    public List<DemandDTO> demands;

    public static class DemandDTO {
        public String subjectName;
        public List<DemandItemDTO> demandItems;
        public boolean active;
        public DemandItemPriority priority;
        public List<CustomDemandItem> customDemandItems;
    }

    public static class DemandItemDTO {
        public String key;
        public DemandItemOperator operator;
        public DemandItemValueDTO value;
        public String description;
    }

    public static interface DemandItemValueDTO {
    }

    public static class DemandItemArrayValueDTO extends ArrayList<String> implements DemandItemValueDTO {
    }

    public static class DemandItemExactValueDTO extends StringValueObject implements DemandItemValueDTO {
        public DemandItemExactValueDTO(String value) throws InvalidInputException {
            super(value);
        }
    }

    public static class DemandItemRangeValueDTO implements DemandItemValueDTO {
        public double minValue;
        public double maxValue;
    }
}
