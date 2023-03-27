package com.gdsc.studiex.domain.suggestion.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.share.models.Pair;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.studier.models.StudierSearchCriteriaDTO;
import com.gdsc.studiex.domain.studier.services.SearchStudierService;
import com.gdsc.studiex.domain.suggestion.models.*;
import com.gdsc.studiex.domain.suggestion.repositories.PastSuggestionRepository;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.DemandsRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SuggestService {
    private final DemandsRepository demandsRepository;
    private final SuppliesRepository suppliesRepository;
    private final PastSuggestionRepository pastSuggestionRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;
    private final SearchStudierService searchStudierService;

    public List<SuggestorResultDTO> suggest(Input input) throws BusinessLogicException {
        if (input.limit <= 0)
            throw new InvalidInputException("limit must be greater than 0");
        final Supplies suppliesOfStudier = suppliesRepository.findByStudierId(input.studierId);
        final Demands demandsOfStudier = demandsRepository.findByStudierId(input.studierId);
        if (suppliesOfStudier == null || demandsOfStudier == null)
            throw new InvalidInputException("Studier is lacking demands or supplies");
        final SuppliesDemands suppliesDemandsOfStudier = SuppliesDemands.builder()
                .demands(demandsOfStudier)
                .supplies(suppliesOfStudier)
                .studierId(input.studierId)
                .build();
        final List<Studier> matchCriteriaStudiers = searchStudierService.searchByCriteria(input.studierId, input.studierSearchCriteria);
        final PastSuggestion pastSuggestion = pastSuggestionRepository.findByStudierId(input.studierId);
        final List<Demands> potentialDemandsList = demandsRepository.findDemandsContains(
                suppliesOfStudier.getAllowedSupplyIds(),
                matchCriteriaStudiers.stream()
                        .map(Studier::getStudierId)
                        .collect(Collectors.toList()),
                pastSuggestion.studierIds()
        );
        final List<Supplies> potentialSuppliesList = suppliesRepository.findSuppliesContains(
                demandsOfStudier.getAllowedSupplyIds(),
                Demands.extractStudierIdsOf(potentialDemandsList)
        );
        final List<SuppliesDemands> potentialSuppliesDemandsList = SuppliesDemands.merge(
                potentialSuppliesList,
                potentialDemandsList
        );
        final List<SuggestorResult> suggestedSuppliesDemandsList = Suggestor.suggest(
                suppliesDemandsOfStudier,
                potentialSuppliesDemandsList
        );
        final List<SuggestorResult> result = sublist(input.limit, suggestedSuppliesDemandsList);

        pastSuggestion.addStudierIds(result.stream()
                .map(SuggestorResult::getStudierId)
                .collect(Collectors.toList()));
        pastSuggestionRepository.save(pastSuggestion);

        final List<Id> allowedSupplyIds = SuggestorResult.extractAllAllowedSupplyId(result);
        final List<AllowedSupply> allowedSupplies = allowedSupplyRepository.findByIds(allowedSupplyIds);

        final Pair<StudierDTO, Studier> studier = searchStudierService.getStudierWithDTO(input.studierId, input.studierId);
        final List<Pair<StudierDTO, Studier>> suggestedStudiers = searchStudierService.getStudiersWithDTO(
                result
                        .stream()
                        .map(suggestorResult -> suggestorResult.getStudierId())
                        .collect(Collectors.toList())
        );
        return result.stream()
                .map(suggestorResult -> SuggestorResultDTO.fromSuggestorResult(
                        suggestorResult,
                        allowedSupplies,
                        findStudierById(suggestorResult.getStudierId(), suggestedStudiers).getFirst(),
                        findStudierById(suggestorResult.getStudierId(), suggestedStudiers).getSecond(),
                        studier.getFirst(),
                        studier.getSecond()))
                .collect(Collectors.toList());
    }

    public Pair<StudierDTO, Studier> findStudierById(Id id, List<Pair<StudierDTO, Studier>> studiers) {
        for (Pair<StudierDTO, Studier> studier : studiers)
            if (studier.getSecond().getStudierId().equals(id))
                return studier;
        return null;
    }

    private List<SuggestorResult> sublist(int limit, List<SuggestorResult> results) {
        if (results.size() <= limit) return results;
        return results.subList(0, limit - 1);
    }

    @Builder
    public static class Input {
        public Id studierId;
        public StudierSearchCriteriaDTO studierSearchCriteria;
        public int limit;
    }

}
