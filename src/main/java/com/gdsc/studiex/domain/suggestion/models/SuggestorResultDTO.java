package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.studier.models.Kilometer;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public class SuggestorResultDTO {
    private SuppliesDemandsDTO suggestedSuppliesDemands;
    private SuppliesDemandsDTO suppliesDemandsOfStudier;
    private List<SuggestedSupplyDTO> suggestedSupplies;
    private List<SuggestedDemandDTO> suggestedDemands;

    private StudierDTO suggestedStudier;
    private Set<String> matchedQualifications;
    private Set<String> matchedPersonalities;
    private Set<String> matchedLikes;
    private Set<String> matchedDislikes;
    private Kilometer distance;
    private Set<String> matchedLifeGoals;
    private Set<String> matchedLearningStyles;
    private Set<String> matchedMajors;

    public static SuggestorResultDTO fromSuggestorResult(SuggestorResult suggestorResult,
                                                         List<AllowedSupply> allowedSupplies,
                                                         StudierDTO suggestedStudierDTO,
                                                         Studier suggestedStudier,
                                                         StudierDTO studierDTO,
                                                         Studier studier) {
        return SuggestorResultDTO.builder()
                .suggestedSuppliesDemands(
                        SuppliesDemandsDTO.fromSuppliesDemands(
                                suggestorResult.getSuggestedSuppliesDemands(),
                                allowedSupplies)
                )
                .suppliesDemandsOfStudier(
                        SuppliesDemandsDTO.fromSuppliesDemands(
                                suggestorResult.getSuppliesDemandsOfStudier(),
                                allowedSupplies)
                )
                .suggestedSupplies(
                        suggestorResult.getSuggestedSupplies()
                                .stream()
                                .map(suggestedSupply -> SuggestedSupplyDTO.builder()
                                        .suggestedSupply(
                                                SuppliesDTO.SupplyDTO.fromSupply(
                                                        suggestedSupply.getSuggestedSupply(),
                                                        AllowedSupply.findAllowedSupplyById(allowedSupplies, suggestedSupply.getSuggestedSupply().getAllowedSupplyId())
                                                )
                                        )
                                        .demandOfStudier(
                                                DemandsDTO.DemandDTO.fromDemand(
                                                        suggestedSupply.getDemandOfStudier(),
                                                        AllowedSupply.findAllowedSupplyById(allowedSupplies, suggestedSupply.getDemandOfStudier().getAllowedSupplyId())
                                                )
                                        )
                                        .matchingItems(
                                                suggestedSupply
                                                        .getMatchingItems()
                                                        .stream()
                                                        .map(matchingItem -> {
                                                            final AllowedSupply allowedSupplyOfSuggestedSupply = AllowedSupply.findAllowedSupplyById(
                                                                    allowedSupplies,
                                                                    suggestedSupply.getSuggestedSupply().getAllowedSupplyId()
                                                            );
                                                            final SuppliesDTO.SupplyItemDTO supplyItemDTO = SuppliesDTO.SupplyItemDTO.fromSupplyItem(
                                                                    matchingItem.getSupplyItem(),
                                                                    allowedSupplyOfSuggestedSupply.findItemById(matchingItem.getSupplyItem().getAllowedSupplyItemId())
                                                            );
                                                            final AllowedSupply allowedSupplyOfDemandOfStudier = AllowedSupply.findAllowedSupplyById(
                                                                    allowedSupplies,
                                                                    suggestedSupply.getDemandOfStudier().getAllowedSupplyId()
                                                            );
                                                            final DemandsDTO.DemandItemDTO demandItemDTO = DemandsDTO.DemandItemDTO.fromDemandItem(
                                                                    matchingItem.getDemandItem(),
                                                                    allowedSupplyOfDemandOfStudier.findItemById(matchingItem.getDemandItem().getAllowedSupplyItemId())
                                                            );
                                                            return MatchingItemDTO.builder()
                                                                    .supplyItem(supplyItemDTO)
                                                                    .demandItem(demandItemDTO)
                                                                    .build();
                                                        })
                                                        .collect(Collectors.toList())
                                        )
                                        .build())
                                .collect(Collectors.toList())
                )
                .suggestedDemands(
                        suggestorResult.getSuggestedDemands()
                                .stream()
                                .map(suggestedDemand -> SuggestedDemandDTO.builder()
                                        .suggestedDemand(
                                                DemandsDTO.DemandDTO.fromDemand(
                                                        suggestedDemand.getSuggestedDemand(),
                                                        AllowedSupply.findAllowedSupplyById(allowedSupplies, suggestedDemand.getSuggestedDemand().getAllowedSupplyId())
                                                )
                                        )
                                        .supplyOfStudier(
                                                SuppliesDTO.SupplyDTO.fromSupply(
                                                        suggestedDemand.getSupplyOfStudier(),
                                                        AllowedSupply.findAllowedSupplyById(allowedSupplies, suggestedDemand.getSupplyOfStudier().getAllowedSupplyId())
                                                )
                                        )
                                        .matchingItems(
                                                suggestedDemand
                                                        .getMatchingItems()
                                                        .stream()
                                                        .map(matchingItem -> {
                                                            final AllowedSupply allowedSupplyOfSuggestedDemand = AllowedSupply.findAllowedSupplyById(
                                                                    allowedSupplies,
                                                                    suggestedDemand.getSuggestedDemand().getAllowedSupplyId()
                                                            );
                                                            final DemandsDTO.DemandItemDTO demandItemDTO = DemandsDTO.DemandItemDTO.fromDemandItem(
                                                                    matchingItem.getDemandItem(),
                                                                    allowedSupplyOfSuggestedDemand.findItemById(matchingItem.getDemandItem().getAllowedSupplyItemId())
                                                            );
                                                            final AllowedSupply allowedSupplyOfSupplyOfStudier = AllowedSupply.findAllowedSupplyById(
                                                                    allowedSupplies,
                                                                    suggestedDemand.getSupplyOfStudier().getAllowedSupplyId()
                                                            );
                                                            final SuppliesDTO.SupplyItemDTO supplyItemDTO = SuppliesDTO.SupplyItemDTO.fromSupplyItem(
                                                                    matchingItem.getSupplyItem(),
                                                                    allowedSupplyOfSupplyOfStudier.findItemById(matchingItem.getSupplyItem().getAllowedSupplyItemId())
                                                            );
                                                            return MatchingItemDTO.builder()
                                                                    .demandItem(demandItemDTO)
                                                                    .supplyItem(supplyItemDTO)
                                                                    .build();
                                                        })
                                                        .collect(Collectors.toList())
                                        )
                                        .build())
                                .collect(Collectors.toList())
                )
                .suggestedStudier(suggestedStudierDTO)
                .matchedQualifications(suggestedStudierDTO.getQualifications() == null ?
                        null :
                        findMatches(studierDTO.getQualifications(), suggestedStudierDTO.getQualifications()))
                .matchedPersonalities(suggestedStudierDTO.getPersonalities() == null ?
                        null :
                        findMatches(studierDTO.getPersonalities(), suggestedStudierDTO.getPersonalities()))
                .matchedLikes(suggestedStudierDTO.getLikes() == null ?
                        null :
                        findMatches(studierDTO.getLikes(), suggestedStudierDTO.getLikes()))
                .matchedDislikes(suggestedStudierDTO.getDislikes() == null ?
                        null :
                        findMatches(studierDTO.getDislikes(), suggestedStudierDTO.getDislikes()))
                .distance(suggestedStudierDTO.getCoordinates() == null ?
                        null :
                        studier.getCoordinates().distanceTo(suggestedStudier.getCoordinates()))
                .matchedLifeGoals(suggestedStudierDTO.getLifeGoals() == null ?
                        null :
                        findMatches(studierDTO.getLifeGoals(), suggestedStudierDTO.getLifeGoals()))
                .matchedLearningStyles(suggestedStudierDTO.getLearningStyles() == null ?
                        null :
                        findMatches(studierDTO.getLearningStyles(), suggestedStudierDTO.getLearningStyles()))
                .matchedMajors(suggestedStudierDTO.getMajors() == null ?
                        null :
                        findMatches(studierDTO.getMajors(), suggestedStudierDTO.getMajors()))
                .build();
    }

    private static Set<String> findMatches(Set<String> setA, Set<String> setB) {
        final Set<String> result = new HashSet<>();
        for (String aE : setA)
            if (setB.contains(aE))
                result.add(aE);
        return result;
    }

    @Getter
    @Builder
    public static class SuggestedSupplyDTO {
        private DemandsDTO.DemandDTO demandOfStudier;
        private SuppliesDTO.SupplyDTO suggestedSupply;
        private List<MatchingItemDTO> matchingItems;
    }

    @Builder
    @Getter
    public static class SuggestedDemandDTO {
        private SuppliesDTO.SupplyDTO supplyOfStudier;
        private DemandsDTO.DemandDTO suggestedDemand;
        private List<MatchingItemDTO> matchingItems;
    }

    @Builder
    @Getter
    public static class MatchingItemDTO {
        private DemandsDTO.DemandItemDTO demandItem;
        private SuppliesDTO.SupplyItemDTO supplyItem;
    }
}
