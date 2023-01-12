package com.gdsc.studiex.domain.supply_and_demand.services.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demand;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandItem;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.DemandsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        return buildDemandsDTO(studierId, demands, allowedSupplies);
    }

    private DemandsDTO buildDemandsDTO(Id studierId, Demands demands, List<AllowedSupply> allowedSupplies) {
        Map<String, AllowedSupply> allowedSupplyMap = allowedSupplies.stream()
                .collect(Collectors.toMap(allowedSupply -> allowedSupply.getId().toString(), allowedSupply -> allowedSupply));
        Map<Demand, AllowedSupply> demandMap = demands.getDemands().stream()
                .collect(Collectors.toMap(
                        demand -> demand,
                        demand -> {
                            Id allowedSupplyId = demand.getAllowedSupplyId();
                            AllowedSupply allowedSupply = allowedSupplyMap.get(allowedSupplyId.toString());
                            return allowedSupply;
                        }
                ));
        List<DemandsDTO.DemandDTO> demandDTOS = new LinkedList<>();
        for (Map.Entry<Demand, AllowedSupply> entry : demandMap.entrySet()) {
            demandDTOS.add(buildDemandDTO(entry.getKey(), entry.getValue()));
        }
        return DemandsDTO.builder()
                .studierId(studierId)
                .demands(demandDTOS)
                .build();
    }

    private DemandsDTO.DemandDTO buildDemandDTO(Demand demand, AllowedSupply allowedSupply) {
        Map<DemandItem, AllowedSupplyItem> demandItemMap = demand.getDemandItems().stream()
                .collect(Collectors.toMap(
                        demandItem -> demandItem,
                        demandItem -> allowedSupply.findItemById(demandItem.getAllowedSupplyItemId())));
        List<DemandsDTO.DemandItemDTO> demandItemDTOS = new LinkedList<>();
        for (Map.Entry<DemandItem, AllowedSupplyItem> entry : demandItemMap.entrySet()) {
            demandItemDTOS.add(buildSupplyItemDTO(entry.getKey(), entry.getValue()));
        }
        return DemandsDTO.DemandDTO.builder()
                .subjectName(allowedSupply.getSubjectName())
                .demandItems(demandItemDTOS)
                .active(demand.isActive())
                .priority(demand.getPriority())
                .customDemandItems(demand.getCustomDemandItems())
                .build();
    }

    private DemandsDTO.DemandItemDTO buildSupplyItemDTO(DemandItem demandItem, AllowedSupplyItem allowedSupplyItem) {
        return DemandsDTO.DemandItemDTO.builder()
                .key(allowedSupplyItem.getKey())
                .value(demandItem.getValue().buildeDemandItemValueDto(allowedSupplyItem.getValue()))
                .operator(demandItem.getOperator())
                .description(demandItem.getDescription())
                .required(demandItem.isRequired())
                .build();
    }
}
