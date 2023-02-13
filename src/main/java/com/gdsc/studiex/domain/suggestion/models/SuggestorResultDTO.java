package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class SuggestorResultDTO {
    private SuppliesDemandsDTO suggestedSuppliesDemands;
    private SuppliesDemandsDTO suppliesDemandsOfStudier;
    private List<SuggestedSupplyDTO> suggestedSupplies;
    private List<SuggestedDemandDTO> suggestedDemands;

    public static SuggestorResultDTO fromSuggestorResult(SuggestorResult suggestorResult, List<AllowedSupply> allowedSupplies) {
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
                .build();
    }

    @Builder
    @Getter
    public static class SuggestedSupplyDTO {
        private DemandsDTO.DemandDTO demandOfStudier;
        private SuppliesDTO.SupplyDTO suggestedSupply;
    }

    @Builder
    @Getter
    public static class SuggestedDemandDTO {
        private SuppliesDTO.SupplyDTO supplyOfStudier;
        private DemandsDTO.DemandDTO suggestedDemand;
    }
}
