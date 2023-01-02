package com.gdsc.studiex.domain.supply_and_demand.services.supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply_and_demand_quota.SupplyAndDemandQuota;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.*;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesQuotaRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaveSuppliesService {
    private final SuppliesRepository suppliesRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;
    private final SuppliesQuotaRepository suppliesQuotaRepository;

    public void saveSupplies(Id studierId, List<SuppliesDTO.SupplyDTO> input) throws InvalidInputException {
        final List<AllowedSupply> allowedSupplies = allowedSupplyRepository
                .findBySubjectNames(input.stream()
                        .map(supply -> supply.subjectName)
                        .collect(Collectors.toList()));
        final List<Supply> supplyList = new ArrayList<>();
        for (SuppliesDTO.SupplyDTO inputSupply : input)
            supplyList.add(buildSupply(inputSupply, allowedSupplies));
        final SupplyAndDemandQuota supplyAndDemandQuota = suppliesQuotaRepository.get();
        final Supplies supplies = Supplies.newSuppliesBuilder()
                .studierId(studierId)
                .supplies(supplyList)
                .supplyAndDemandQuota(supplyAndDemandQuota)
                .build();
        suppliesRepository.save(supplies);
    }

    private Supply buildSupply(SuppliesDTO.SupplyDTO inputSupply, List<AllowedSupply> allowedSupplies) throws InvalidInputException {
        final AllowedSupply allowedSupply = findAllowedSupplyBySubjectName(allowedSupplies, inputSupply.subjectName);
        if (allowedSupply == null)
            throw new InvalidInputException("There are no Allowed Supply with subject name: " + inputSupply.subjectName);
        final List<SupplyItem> supplyItems = new ArrayList<>();
        for (SuppliesDTO.SupplyItemDTO inputSupplyItem : inputSupply.supplyItems)
            supplyItems.add(SupplyItem.fromAllowedSupplyBuilder()
                    .key(inputSupplyItem.key)
                    .operator(inputSupplyItem.operator)
                    .value(inputSupplyItem.value)
                    .description(inputSupplyItem.description)
                    .allowedSupply(allowedSupply)
                    .build());
        final Supply supply = Supply.fromAllowedSupplyBuilder()
                .supplyItems(supplyItems)
                .active(inputSupply.active)
                .priority(inputSupply.priority)
                .customSupplyItems(inputSupply.customSupplyItems)
                .allowedSupply(allowedSupply)
                .build();
        return supply;
    }

    private AllowedSupply findAllowedSupplyBySubjectName(List<AllowedSupply> allowedSupplies, String subjectName) {
        for (AllowedSupply allowedSupply : allowedSupplies)
            if (allowedSupply.getSubjectName().equals(subjectName))
                return allowedSupply;
        return null;
    }
}
