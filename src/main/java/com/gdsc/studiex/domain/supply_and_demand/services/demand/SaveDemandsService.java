package com.gdsc.studiex.domain.supply_and_demand.services.demand;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItem;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota.SupplyAndDemandQuota;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.DemandsRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesQuotaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaveDemandsService {
    private final AllowedSupplyRepository allowedSupplyRepository;
    private final SuppliesQuotaRepository suppliesQuotaRepository;
    private final DemandsRepository demandsRepository;

    public void saveDemands(Id studierId, List<DemandsDTO.DemandDTO> input) {
        final List<AllowedSupply> allowedSupplies = allowedSupplyRepository
                .findBySubjectNames(input.stream()
                        .map(demand -> demand.subjectName)
                        .collect(Collectors.toList()));
        final List<Demand> demandList = new ArrayList<>();
        for (DemandsDTO.DemandDTO inputDemand : input)
            demandList.add(buildDemand(inputDemand, allowedSupplies));
        final SupplyAndDemandQuota supplyAndDemandQuota = suppliesQuotaRepository.get();
        final Demands demands = Demands.newDemandsBuilder()
                .demands(demandList)
                .studierId(studierId)
                .supplyAndDemandQuota(supplyAndDemandQuota)
                .build();
        demandsRepository.save(demands);
    }

    private Demand buildDemand(DemandsDTO.DemandDTO inputDemand, List<AllowedSupply> allowedSupplies) {
        final AllowedSupply allowedSupply = AllowedSupply.findAllowedSupplyBySubjectName(allowedSupplies, inputDemand.subjectName);
        if (allowedSupply == null)
            throw new InvalidInputException("There are no Allowed Supply with subject name: " + inputDemand.subjectName);
        final List<DemandItem> demandItems = new ArrayList<>();
        for (DemandsDTO.DemandItemDTO inputDemandItem : inputDemand.demandItems)
            demandItems.add(DemandItem.fromAllowedSupplyBuilder()
                    .key(inputDemandItem.key)
                    .operator(inputDemandItem.operator)
                    .value(inputDemandItem.value)
                    .description(inputDemandItem.description)
                    .allowedSupply(allowedSupply)
                    .required(inputDemandItem.required)
                    .build());
        final Demand demand = Demand.fromAllowedSupplyBuilder()
                .demandItems(demandItems)
                .active(inputDemand.active)
                .priority(inputDemand.priority)
                .customDemandItems(inputDemand.customDemandItems)
                .allowedSupply(allowedSupply)
                .build();
        return demand;
    }
}
