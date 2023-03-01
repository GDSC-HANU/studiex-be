package com.gdsc.studiex.domain.supply_and_demand.services.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpdateSuppliesService {
    private final SuppliesRepository suppliesRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;

    public void updateSupply(Id studierId, Id supplyId, SuppliesDTO.SupplyDTO supplyDTO) {
        Supplies supplies = suppliesRepository.findByStudierId(studierId);
        AllowedSupply allowedSupply = allowedSupplyRepository.findBySubjectName(supplyDTO.subjectName);
        for(Supply supply : supplies.getSupplies()) {
            if(supply.getAllowedSupplyId().equals(supplyId)) {
                List<SupplyItem> supplyItems = new ArrayList<>();
                for (SuppliesDTO.SupplyItemDTO supplyItemDTO : supplyDTO.supplyItems) {
                    supplyItems.add(SupplyItem.fromAllowedSupplyBuilder()
                            .key(supplyItemDTO.key)
                            .operator(supplyItemDTO.operator)
                            .value(supplyItemDTO.value)
                            .description(supplyItemDTO.description)
                            .allowedSupply(allowedSupply)
                            .build());
                }
                supply.setSupplyItems(supplyItems);
                supply.setActive(supplyDTO.active);
                supply.setPriority(supplyDTO.priority);
                supply.setCustomSupplyItems(supplyDTO.customSupplyItems);
            }
        }
        suppliesRepository.save(supplies);
    }
}
