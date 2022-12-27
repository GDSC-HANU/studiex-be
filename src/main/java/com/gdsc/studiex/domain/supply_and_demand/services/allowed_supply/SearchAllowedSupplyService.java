package com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchAllowedSupplyService {
    private final AllowedSupplyRepository allowedSupplyRepository;

    public List<AllowedSupply> searchAllowedSupply(int page, int perPage) {
        if (page <= 0) page = 1;
        return allowedSupplyRepository.findPaging(page, perPage);
    }
}
