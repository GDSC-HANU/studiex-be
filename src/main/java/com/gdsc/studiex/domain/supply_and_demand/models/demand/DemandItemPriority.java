package com.gdsc.studiex.domain.supply_and_demand.models.demand;

import java.util.Map;

public enum DemandItemPriority {
    HIGH,
    MEDIUM,
    LOW;

    private static Map<DemandItemPriority, Integer> priorityIntegerMap = Map.of(
            HIGH, 2,
            MEDIUM, 1,
            LOW, 0
    );

    public static int compare(DemandItemPriority first, DemandItemPriority second) {
        if (priorityIntegerMap.get(first) < priorityIntegerMap.get(second))
            return -1;
        else if (priorityIntegerMap.get(first) > priorityIntegerMap.get(second))
            return 1;
        return 0;
    }
}
