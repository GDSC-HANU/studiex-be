package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class SuggestorResult {
    private SuppliesDemands suggestedSuppliesDemands;
    private SuppliesDemands suppliesDemandsOfStudier;
    private List<SuggestedSupply> suggestedSupplies;
    private List<SuggestedDemand> suggestedDemands;
    private double score;

    public static List<Id> extractAllAllowedSupplyId(List<SuggestorResult> suggestorResults) {
        final Set<Id> result = new HashSet<>();
        for (SuggestorResult suggestorResult : suggestorResults) {
            result.addAll(suggestorResult.suggestedSuppliesDemands.getSupplies().getAllowedSupplyIds());
            result.addAll(suggestorResult.suggestedSuppliesDemands.getDemands().getAllowedSupplyIds());

            result.addAll(suggestorResult.suppliesDemandsOfStudier.getSupplies().getAllowedSupplyIds());
            result.addAll(suggestorResult.suppliesDemandsOfStudier.getDemands().getAllowedSupplyIds());
        }
        return result.stream().collect(Collectors.toList());
    }

    public Id getStudierId() {
        return suggestedSuppliesDemands.getStudierId();
    }

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
}
