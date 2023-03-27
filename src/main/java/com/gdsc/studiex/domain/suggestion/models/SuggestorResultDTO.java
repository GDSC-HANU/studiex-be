package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Coordinates;
import com.gdsc.studiex.domain.studier.models.Kilometer;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
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
    public static class SuggestedSupplyDTO {
        private DemandsDTO.DemandDTO demandOfStudier;
        private SuppliesDTO.SupplyDTO suggestedSupply;
        private List<MatchingItem> matchingItems;

        @Builder
        public SuggestedSupplyDTO(DemandsDTO.DemandDTO demandOfStudier, SuppliesDTO.SupplyDTO suggestedSupply) {
            this.demandOfStudier = demandOfStudier;
            this.suggestedSupply = suggestedSupply;
        }
    }

    @Builder
    @Getter
    public static class SuggestedDemandDTO {
        private SuppliesDTO.SupplyDTO supplyOfStudier;
        private DemandsDTO.DemandDTO suggestedDemand;
        // TODO: add this
        private List<MatchingItem> matchingItems;
    }

    public static class MatchingItem {
        DemandsDTO.DemandItemDTO demandItem;
        SuppliesDTO.SupplyItemDTO supplyItem;
    }
}
