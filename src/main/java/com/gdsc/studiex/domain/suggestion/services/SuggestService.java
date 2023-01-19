package com.gdsc.studiex.domain.suggestion.services;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.suggestion.models.Suggestor;
import com.gdsc.studiex.domain.suggestion.models.SuggestorResult;
import com.gdsc.studiex.domain.suggestion.models.SuppliesDemands;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.repositories.DemandsRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SuggestService {
    private final DemandsRepository demandsRepository;
    private final SuppliesRepository suppliesRepository;

    public List<SuggestorResult> suggest(Id studierId, int limit) {
        if (limit <= 0)
            throw new InvalidInputException("limit must be greater than 0");
        final Supplies suppliesOfStudier = suppliesRepository.findByStudierId(studierId);
        final Demands demandsOfStudier = demandsRepository.findByStudierId(studierId);
        if (suppliesOfStudier == null || demandsOfStudier == null)
            throw new InvalidInputException("Studier is lacking demands or supplies");
        final SuppliesDemands suppliesDemandsOfStudier = SuppliesDemands.builder()
                .demands(demandsOfStudier)
                .supplies(suppliesOfStudier)
                .studierId(studierId)
                .build();
        final List<Demands> potentialDemandsList = demandsRepository.findDemandsContains(
                suppliesOfStudier.getAllowedSupplyIds()
        );
        final List<Supplies> potentialSuppliesList = suppliesRepository.findSuppliesContains(
                demandsOfStudier.getAllowedSupplyIds(),
                Demands.extractStudierIdsOf(potentialDemandsList)
        );
        final List<SuppliesDemands> potentialSuppliesDemandsList = SuppliesDemands.merge(
                potentialSuppliesList,
                potentialDemandsList
        );
        final List<SuggestorResult> suggestedSuppliesDemandsList = Suggestor.suggestSupplyDemand(
                suppliesDemandsOfStudier,
                potentialSuppliesDemandsList
        );
        if (suggestedSuppliesDemandsList.size() <= limit)
            return suggestedSuppliesDemandsList;
        else
            return suggestedSuppliesDemandsList.subList(0, limit - 1);
    }
}
