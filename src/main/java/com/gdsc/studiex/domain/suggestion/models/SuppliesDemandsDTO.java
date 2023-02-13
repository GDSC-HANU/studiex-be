package com.gdsc.studiex.domain.suggestion.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import lombok.Builder;

import java.util.List;

@Builder
public class SuppliesDemandsDTO {
    private Id studierId;
    private SuppliesDTO supplies;
    private DemandsDTO demands;

    public static SuppliesDemandsDTO fromSuppliesDemands(SuppliesDemands suppliesDemands, List<AllowedSupply> allowedSupplies) {
        return SuppliesDemandsDTO.builder()
                .studierId(suppliesDemands.getStudierId())
                .supplies(SuppliesDTO.fromSupplies(suppliesDemands.getSupplies(), allowedSupplies))
                .demands(DemandsDTO.fromDemands(suppliesDemands.getDemands(), allowedSupplies))
                .build();
    }
}
