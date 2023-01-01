package com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchAllowedSupplyService {
    private final AllowedSupplyRepository allowedSupplyRepository;

    public List<AllowedSupplyDTO> searchAllowedSupply(int page, int perPage) {
        if (page <= 0) page = 1;
        final List<AllowedSupply> allowedSupplies = allowedSupplyRepository.findPaging(page, perPage);
        return allowedSupplies.stream()
                .map(allowedSupply -> AllowedSupplyDTO.fromAllowedSupply(allowedSupply))
                .collect(Collectors.toList());
    }
}
