package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class SuppliesDemands {
    private Id studierId;
    private Supplies supplies;
    private Demands demands;

    public static List<SuppliesDemands> merge(List<Supplies> suppliesList, List<Demands> demandsList) {
        final Map<Id, Supplies> studierSupplies = new HashMap<>();
        for (Supplies supplies : suppliesList)
            studierSupplies.put(supplies.getStudierId(), supplies);
        final List<SuppliesDemands> result = new ArrayList<>();
        for (Demands demands : demandsList) {
            final Supplies supplies = studierSupplies.get(demands.getStudierId());
            if (supplies != null)
                result.add(SuppliesDemands.builder()
                        .supplies(supplies)
                        .demands(demands)
                        .studierId(supplies.getStudierId())
                        .build());
        }
        return result;
    }
}
