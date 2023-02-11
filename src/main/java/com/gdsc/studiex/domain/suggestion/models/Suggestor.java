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
import java.util.stream.Collectors;

public class Suggestor {
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

    public static List<SuggestorResult> suggest(SuppliesDemands suppliesDemandsOfStudier,
                                                List<SuppliesDemands> potentialSuppliesDemandsList) {
        final List<SuggestorResult> result = new ArrayList<>();
        for (SuppliesDemands potentialSuppliesDemands : potentialSuppliesDemandsList) {
            final List<MatchingSupplyDemandPair> matchingDemandsForStudier = findMatchingSupplyDemandPairs(
                    suppliesDemandsOfStudier.getSupplies(),
                    potentialSuppliesDemands.getDemands()
            );
            final List<MatchingSupplyDemandPair> matchingSuppliesForStudier = findMatchingSupplyDemandPairs(
                    potentialSuppliesDemands.getSupplies(),
                    suppliesDemandsOfStudier.getDemands()
            );
            final double score = calculateScoreMatchingSupplyDemandPairs(matchingDemandsForStudier, matchingSuppliesForStudier);
            result.add(SuggestorResult.builder()
                    .suggestedSuppliesDemands(potentialSuppliesDemands)
                    .score(score)
                    .suggestedDemands(matchingDemandsForStudier.stream()
                            .map(matchingSupplyDemandPair -> SuggestorResult.SuggestedDemand.builder()
                                    .suggestedDemand(matchingSupplyDemandPair.demand)
                                    .supplyOfStudier(matchingSupplyDemandPair.supply)
                                    .build())
                            .collect(Collectors.toList()))
                    .suggestedSupplies(matchingSuppliesForStudier.stream()
                            .map(matchingSupplyDemandPair -> SuggestorResult.SuggestedSupply.builder()
                                    .suggestedSupply(matchingSupplyDemandPair.supply)
                                    .demandOfStudier(matchingSupplyDemandPair.demand)
                                    .build())
                            .collect(Collectors.toList()))
                    .build());
        }
        return sortSuggestorResultByScoreDesc(result);
    }

    public static double calculateScoreMatchingSupplyDemandPairs(List<MatchingSupplyDemandPair> matchingDemandsForStudier,
                                                                 List<MatchingSupplyDemandPair> matchingSuppliesForStudier) {
        final double matchingDemandsMatchScore = sumMatchScore(matchingDemandsForStudier);
        final double matchingSuppliesMatchScore = sumMatchScore(matchingSuppliesForStudier);
        final double sum = matchingDemandsMatchScore + matchingSuppliesMatchScore;
        final double diff = Math.abs(matchingDemandsMatchScore - matchingSuppliesMatchScore);
        return sum / diff;
    }

    public static double sumMatchScore(List<MatchingSupplyDemandPair> matchingSupplyDemandPairList) {
        double result = 0;
        for (MatchingSupplyDemandPair pair : matchingSupplyDemandPairList)
            result += pair.matchScore;
        return result;
    }

    public static List<SuggestorResult> sortSuggestorResultByScoreDesc(List<SuggestorResult> list) {
        final List<SuggestorResult> result = new ArrayList<>(list);
        result.sort(Comparator.comparingDouble(SuggestorResult::getScore));
        Collections.reverse(result);
        return Collections.unmodifiableList(result);
    }

    public static int getWeight(Pair<DemandPriority, SupplyPriority> priorityPair) {
        return priorityPairWeights.get(priorityPair);
    }

    public static List<MatchingSupplyDemandPair> findMatchingSupplyDemandPairs(Supplies supplies, Demands demands) {
        final List<MatchingSupplyDemandPair> matchingSupplyDemandPairs = new ArrayList<>();
        for (Supply supply : supplies.getSupplies())
            for (Demand demand : demands.getDemands())
                if (supply.getAllowedSupplyId().equals(demand.getAllowedSupplyId())) {
                    final int weight = priorityPairWeights.get(new Pair<>(
                            demand.getPriority(),
                            supply.getPriority()
                    ));
                    final double score = calculateMatchScore(supply, demand, weight);
                    matchingSupplyDemandPairs.add(MatchingSupplyDemandPair.builder()
                            .matchScore(score)
                            .demand(demand)
                            .supply(supply)
                            .build());
                }
        return matchingSupplyDemandPairs;
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


    @Builder
    @Getter
    public static class MatchingSupplyDemandPair {
        private Supply supply;
        private Demand demand;
        private double matchScore;
    }
}
