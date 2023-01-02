package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.StringValueObject;

import java.util.ArrayList;

public class DemandsDTO {
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
