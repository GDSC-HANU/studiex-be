package com.gdsc.studiex.domain.supply_and_demand.services.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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
        return buildSuppliesDTO(studierId, supplies, allowedSupplies);
    }

    private SuppliesDTO buildSuppliesDTO(Id studierId, Supplies supplies, List<AllowedSupply> allowedSupplies) {
        Map<Id, AllowedSupply> allowedSupplyMap = allowedSupplies.stream()
                .collect(Collectors.toMap(AllowedSupply::getId, allowedSupply -> allowedSupply));
        Map<Supply, AllowedSupply> supplyMap = supplies.getSupplies().stream()
                        .collect(Collectors.toMap(
                                supply -> supply,
                                supply -> allowedSupplyMap.get(supply.getAllowedSupplyId())
                        ));
        List<SuppliesDTO.SupplyDTO> supplyDTOs = new LinkedList<>();
        for(Map.Entry<Supply, AllowedSupply> entry : supplyMap.entrySet()) {
            supplyDTOs.add(buildSupplyDTO(entry.getKey(), entry.getValue()));
        }
        return SuppliesDTO.builder()
                .studierId(studierId)
                .supplies(supplyDTOs)
                .build();
    }

    private SuppliesDTO.SupplyDTO buildSupplyDTO(Supply supply, AllowedSupply allowedSupply) {
        Map<SupplyItem, AllowedSupplyItem> supplyItemMap = supply.getSupplyItems().stream()
                .collect(Collectors.toMap(
                        supplyItem -> supplyItem,
                        supplyItem -> allowedSupply.findItemById(supplyItem.getAllowedSupplyItemId())));
        List<SuppliesDTO.SupplyItemDTO> supplyItemDTOS = new LinkedList<>();
        for(Map.Entry<SupplyItem, AllowedSupplyItem> entry : supplyItemMap.entrySet()) {
            supplyItemDTOS.add(buildSupplyItemDTO(entry.getKey(), entry.getValue()));
        }
        return SuppliesDTO.SupplyDTO.builder()
                .subjectName(allowedSupply.getSubjectName())
                .supplyItems(supplyItemDTOS)
                .active(supply.isActive())
                .priority(supply.getPriority())
                .customSupplyItems(supply.getCustomSupplyItems())
                .build();
    }

    private SuppliesDTO.SupplyItemDTO buildSupplyItemDTO(SupplyItem supplyItem, AllowedSupplyItem allowedSupplyItem) {
        return SuppliesDTO.SupplyItemDTO.builder()
                .key(allowedSupplyItem.getKey())
                .value(SuppliesDTO.SupplyItemDTO.buildSupplyItemValueDTO(supplyItem.getOperator(), supplyItem.getValue(), allowedSupplyItem.getValue()))
                .operator(supplyItem.getOperator())
                .description(supplyItem.getDescription())
                .build();
    }
}
