package com.gdsc.studiex.domain.supply_and_demand.services.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchSuppliesService {
    private final SuppliesRepository suppliesRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;

    public SuppliesDTO getSupplies(Id studierId) {
        Supplies supplies = suppliesRepository.findByStudierId(studierId);
        List<AllowedSupply> allowedSupplies = allowedSupplyRepository.findByIds(
                supplies.getSupplies().stream()
                        .map(Supply::getAllowedSupplyId)
                        .collect(Collectors.toList())
        );
        return SuppliesDTO.fromSupplies(supplies, allowedSupplies);
    }
}
