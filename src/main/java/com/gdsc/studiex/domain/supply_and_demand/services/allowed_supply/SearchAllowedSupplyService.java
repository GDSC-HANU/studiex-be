package com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;

import java.util.List;

public class SearchAllowedSupplyService {
    public List<AllowedSupply> searchAllowedSupply(int page, int perPage) {
        if (page <= 0) page = 1;
        return null;
    }
}
