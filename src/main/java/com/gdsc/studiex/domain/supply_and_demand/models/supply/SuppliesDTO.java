package com.gdsc.studiex.domain.supply_and_demand.models.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.StringValueObject;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.CustomSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyPriority;

import java.util.ArrayList;
import java.util.List;

public class SuppliesDTO {
    public Id studierId;
    public List<SupplyDTO> supplies;

    public static class SupplyDTO {
        public String subjectName;
        public List<SupplyItemDTO> supplyItems;
        public boolean active;
        public SupplyPriority priority;
        public List<CustomSupplyItem> customSupplyItems;
    }

    public static class SupplyItemDTO {
        public String key;
        public SupplyItemOperator operator;
        public SupplyItemValueDTO value;
        public String description;
    }

    public static interface SupplyItemValueDTO {
    }

    public static class SupplyItemArrayValueDTO extends ArrayList<String> implements SupplyItemValueDTO {
    }

    public static class SupplyItemExactValueDTO extends StringValueObject implements SupplyItemValueDTO {
        public SupplyItemExactValueDTO(String value) throws InvalidInputException {
            super(value);
        }
    }

    public static class SupplyItemRangeValueDTO  implements SupplyItemValueDTO {
        public double minValue;
        public double maxValue;
    }
}
