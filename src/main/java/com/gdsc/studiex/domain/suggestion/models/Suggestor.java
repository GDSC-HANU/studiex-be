package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Pair;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItem;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandPriority;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyPriority;
import lombok.Builder;

import java.util.*;

public class Suggestor {
    public static List<SuggestorResult> suggestSupplyDemand(SuppliesDemands suppliesDemandsOfStudier,
                                                            List<SuppliesDemands> potentialSuppliesDemandsList) {
        final List<SuggestorResult> result = new ArrayList<>();
        for (SuppliesDemands potentialSuppliesDemands : potentialSuppliesDemandsList) {
            final MatchScore SDMatchScore = suggestSupplyDemand(
                    suppliesDemandsOfStudier.getSupplies(),
                    potentialSuppliesDemands.getDemands()
            );
            final MatchScore DSMatchScore = suggestSupplyDemand(
                    potentialSuppliesDemands.getSupplies(),
                    suppliesDemandsOfStudier.getDemands()
            );
            final double matchScore = (SDMatchScore.score + DSMatchScore.score) / Math.abs(SDMatchScore.score - DSMatchScore.score);
            result.add(SuggestorResult.builder()
                    .suggestedSuppliesDemands(potentialSuppliesDemands)
                    .matchScore(matchScore)
                    .suggestedDemand(SuggestorResult.SuggestedDemand.builder()
                            .suggestedDemand(SDMatchScore.demand)
                            .supplyOfStudier(SDMatchScore.supply)
                            .build())
                    .suggestedSupply(SuggestorResult.SuggestedSupply.builder()
                            .suggestedSupply(DSMatchScore.supply)
                            .demandOfStudier(DSMatchScore.demand)
                            .build())
                    .build());
        }
        result.sort(Comparator.comparingDouble(SuggestorResult::getMatchScore));
        Collections.reverse(result);
        return Collections.unmodifiableList(result);
    }

    private static Map<Pair<DemandPriority, SupplyPriority>, Integer> priorityPairWeights = Map.of(
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.HIGH, SupplyPriority.HIGH), 10,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.HIGH, SupplyPriority.MEDIUM), 8,
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.MEDIUM, SupplyPriority.HIGH), 8,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.MEDIUM, SupplyPriority.MEDIUM), 6,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.HIGH, SupplyPriority.LOW), 4,
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.LOW, SupplyPriority.HIGH), 4,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.MEDIUM, SupplyPriority.LOW), 2,
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.LOW, SupplyPriority.MEDIUM), 2,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.LOW, SupplyPriority.LOW), 1
    );

    @Builder
    private static class MatchScore {
        private Supply supply;
        private Demand demand;
        private double score;
    }

    private static MatchScore suggestSupplyDemand(Supplies supplies, Demands demands) {
        final List<MatchScore> matchScores = new ArrayList<>();
        for (Map.Entry<Pair<DemandPriority, SupplyPriority>, Integer> priorityPairWeight : priorityPairWeights.entrySet()) {
            final Pair<DemandPriority, SupplyPriority> priorityPair = priorityPairWeight.getKey();
            final int weight = priorityPairWeight.getValue();
            for (Supply supply : supplies.getSupplies())
                for (Demand demand : demands.getDemands())
                    if (supply.getPriority().equals(priorityPair.getSecond()) && demand.getPriority().equals(priorityPair.getFirst())) {
                        final double score = calculateSupplyDemandScore(supply, demand, weight);
                        matchScores.add(MatchScore.builder()
                                .score(score)
                                .demand(demand)
                                .supply(supply)
                                .build());
                    }
        }
        return maxSupplyDemandScore(matchScores);
    }

    private static double calculateSupplyDemandScore(Supply supply, Demand demand, int weight) {
        if (!demand.matchAllRequiredDemandItems(supply))
            return 0;
        final int totalCriteria = demand.totalCriteria();
        int matchCriteria = 0;
        for (DemandItem demandItem : demand.getDemandItems())
            for (SupplyItem supplyItem : supply.getSupplyItems())
                if (demandItem.match(supplyItem))
                    matchCriteria += demandItem.matchCriteria(supplyItem);
        return (double) weight * (double) matchCriteria / (double) totalCriteria;
    }

    private static MatchScore maxSupplyDemandScore(List<MatchScore> supplyDemandScores) {
        MatchScore result = null;
        for (MatchScore matchScore : supplyDemandScores)
            if (result == null || matchScore.score > result.score)
                result = matchScore;
        return result;
    }
}
