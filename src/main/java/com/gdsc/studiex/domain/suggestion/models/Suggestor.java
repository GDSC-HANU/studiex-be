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
import lombok.Getter;

import java.util.*;

public class Suggestor {
    public static List<SuggestorResult> suggestSupplyDemand(SuppliesDemands suppliesDemandsOfStudier,
                                                            List<SuppliesDemands> potentialSuppliesDemandsList) {
        final List<SuggestorResult> result = new ArrayList<>();
        for (SuppliesDemands potentialSuppliesDemands : potentialSuppliesDemandsList) {
            final OptimalSupplyDemandPair optimalDemandForStudier = findOptimalSupplyDemandPair(
                    suppliesDemandsOfStudier.getSupplies(),
                    potentialSuppliesDemands.getDemands()
            );
            final OptimalSupplyDemandPair optimalSupplyForStudier = findOptimalSupplyDemandPair(
                    potentialSuppliesDemands.getSupplies(),
                    suppliesDemandsOfStudier.getDemands()
            );
            final double matchScore = (optimalDemandForStudier.matchScore + optimalSupplyForStudier.matchScore) / Math.abs(optimalDemandForStudier.matchScore - optimalSupplyForStudier.matchScore);
            result.add(SuggestorResult.builder()
                    .suggestedSuppliesDemands(potentialSuppliesDemands)
                    .matchScore(matchScore)
                    .suggestedDemand(SuggestorResult.SuggestedDemand.builder()
                            .suggestedDemand(optimalDemandForStudier.demand)
                            .supplyOfStudier(optimalDemandForStudier.supply)
                            .build())
                    .suggestedSupply(SuggestorResult.SuggestedSupply.builder()
                            .suggestedSupply(optimalSupplyForStudier.supply)
                            .demandOfStudier(optimalSupplyForStudier.demand)
                            .build())
                    .build());
        }
        result.sort(Comparator.comparingDouble(SuggestorResult::getMatchScore));
        Collections.reverse(result);
        return Collections.unmodifiableList(result);
    }

    private static final Map<Pair<DemandPriority, SupplyPriority>, Integer> priorityPairWeights = Map.of(
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.HIGH, SupplyPriority.HIGH), 17,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.HIGH, SupplyPriority.MEDIUM), 13,
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.MEDIUM, SupplyPriority.HIGH), 13,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.MEDIUM, SupplyPriority.MEDIUM), 8,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.HIGH, SupplyPriority.LOW), 5,
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.LOW, SupplyPriority.HIGH), 5,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.MEDIUM, SupplyPriority.LOW), 2,
            new Pair<DemandPriority, SupplyPriority>(DemandPriority.LOW, SupplyPriority.MEDIUM), 2,

            new Pair<DemandPriority, SupplyPriority>(DemandPriority.LOW, SupplyPriority.LOW), 1
    );

    public static int getWeight(Pair<DemandPriority, SupplyPriority> priorityPair) {
        return priorityPairWeights.get(priorityPair);
    }

    @Builder
    @Getter
    public static class OptimalSupplyDemandPair {
        private Supply supply;
        private Demand demand;
        private double matchScore;
    }

    public static OptimalSupplyDemandPair findOptimalSupplyDemandPair(Supplies supplies, Demands demands) {
        final List<OptimalSupplyDemandPair> optimalSupplyDemandPairs = new ArrayList<>();
        for (Supply supply : supplies.getSupplies())
            for (Demand demand : demands.getDemands())
                if (supply.getAllowedSupplyId().equals(demand.getAllowedSupplyId())) {
                    final int weight = priorityPairWeights.get(new Pair<>(
                            demand.getPriority(),
                            supply.getPriority()
                    ));
                    final double score = calculateMatchScore(supply, demand, weight);
                    optimalSupplyDemandPairs.add(OptimalSupplyDemandPair.builder()
                            .matchScore(score)
                            .demand(demand)
                            .supply(supply)
                            .build());
                }
        return maxMatchScore(optimalSupplyDemandPairs);
    }

    public static double calculateMatchScore(Supply supply, Demand demand, int weight) {
        if (!demand.matchAllRequiredDemandItems(supply))
            return 0;
        final int totalCriteria = demand.totalCriteria();
        int matchCriteria = 0;
        for (DemandItem demandItem : demand.getDemandItems())
            for (SupplyItem supplyItem : supply.getSupplyItems())
                if (demandItem.match(supplyItem))
                    matchCriteria += demandItem.matchCriteria(supplyItem);
        return (double) weight * 100 * (double) matchCriteria / (double) totalCriteria;
    }

    public static OptimalSupplyDemandPair maxMatchScore(List<OptimalSupplyDemandPair> supplyDemandScores) {
        OptimalSupplyDemandPair result = null;
        for (OptimalSupplyDemandPair optimalSupplyDemandPair : supplyDemandScores)
            if (result == null || optimalSupplyDemandPair.matchScore > result.matchScore)
                result = optimalSupplyDemandPair;
        return result;
    }
}
