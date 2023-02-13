package com.gdsc.studiex.domain.supply_and_demand.services.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.DemandsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SearchDemandsService {
    private final DemandsRepository demandsRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;

    public DemandsDTO searchDemands(Id studierId) {
        Demands demands = demandsRepository.findByStudierId(studierId);
        List<AllowedSupply> allowedSupplies = allowedSupplyRepository.findByIds(
                demands.getDemands().stream()
                        .map(Demand::getAllowedSupplyId)
                        .collect(Collectors.toList())
        );
        return DemandsDTO.fromDemands(demands, allowedSupplies);
    }
}
