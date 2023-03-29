package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
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

    @Getter
    public static class SuggestedSupply {
        private Demand demandOfStudier;
        private Supply suggestedSupply;
        private List<MatchingItem> matchingItems;

        @Builder
        public SuggestedSupply(Demand demandOfStudier, Supply suggestedSupply) {
            this.demandOfStudier = demandOfStudier;
            this.suggestedSupply = suggestedSupply;
            this.matchingItems = new ArrayList<>();
            for (DemandItem demandItem : this.demandOfStudier.getDemandItems())
                for (SupplyItem supplyItem : this.suggestedSupply.getSupplyItems())
                    if (demandItem.match(supplyItem))
                        matchingItems.add(MatchingItem.builder()
                                .demandItem(demandItem)
                                .supplyItem(supplyItem)
                                .build());
        }
    }

    @Getter
    public static class SuggestedDemand {
        private Supply supplyOfStudier;
        private Demand suggestedDemand;
        private List<MatchingItem> matchingItems;

        @Builder
        public SuggestedDemand(Supply supplyOfStudier, Demand suggestedDemand) {
            this.supplyOfStudier = supplyOfStudier;
            this.suggestedDemand = suggestedDemand;
            for (SupplyItem supplyItem : supplyOfStudier.getSupplyItems())
                for (DemandItem demandItem : suggestedDemand.getDemandItems())
                    if (demandItem.match(supplyItem))
                        matchingItems.add(MatchingItem.builder()
                                .demandItem(demandItem)
                                .supplyItem(supplyItem)
                                .build());
        }
    }

    @Builder
    @Getter
    public static class MatchingItem {
        private DemandItem demandItem;
        private SupplyItem supplyItem;
    }
}
