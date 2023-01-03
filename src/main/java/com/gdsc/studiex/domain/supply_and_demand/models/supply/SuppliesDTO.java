package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

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
