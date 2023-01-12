package com.gdsc.studiex.domain.suggestion.services;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
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

    public void suggest(Id studierId, int limit) {
        final Demands demands = demandsRepository.findByStudierId(studierId);
        if (demands.getDemands().isEmpty())
            throw new InvalidInputException("You must add more Demand");
        for (Demand demand : demands.getDemandsSortedByPriority()) {
            final List<Supplies> suppliesList = suppliesRepository.findSuppliesContains(demand.getAllowedSupplyId());

        }
    }
}
