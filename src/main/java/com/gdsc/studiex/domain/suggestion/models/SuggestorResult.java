package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuggestorResult {
    @Builder
    @Getter
    public static class SuggestedSupply {
        private Demand demandOfStudier;
        private Supply suggestedSupply;
    }

    @Builder
    @Getter
    public static class SuggestedDemand {
        private Supply supplyOfStudier;
        private Demand suggestedDemand;
    }

    private SuppliesDemands suggestedSuppliesDemands;
    private SuppliesDemands suppliesDemandsOfStudier;
    private SuggestedSupply suggestedSupply;
    private SuggestedDemand suggestedDemand;
    private double matchScore;
}
