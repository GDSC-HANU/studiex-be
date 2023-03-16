package com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.*;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UpdateAllowedSupplyService {
    private final AllowedSupplyRepository allowedSupplyRepository;

    public void updateAllowedSupply(AllowedSupplyDTO input) throws BusinessLogicException {
        AllowedSupply allowedSupply = allowedSupplyRepository.findById(input.id);
        if(allowedSupply == null) {
            throw new BusinessLogicException("Not Found Allowed Supply", "NOT_FOUND");
        }
        if(input.subjectName != null) {
            allowedSupply.setSubjectName(input.subjectName);
        }
        Map<Id, AllowedSupplyDTO.AllowedSupplyItemDTO> allowedSupplyItemDTOMap = input.allowedSupplyItems.stream()
                .collect(Collectors.toMap(AllowedSupplyDTO.AllowedSupplyItemDTO::getId, allowedSupplyItemDTO -> allowedSupplyItemDTO));
        for(AllowedSupplyItem allowedSupplyItem : allowedSupply.getAllowedSupplyItems()) {
            AllowedSupplyDTO.AllowedSupplyItemDTO allowedSupplyItemDTO = allowedSupplyItemDTOMap.get(allowedSupplyItem.getId());
            if(allowedSupplyItemDTO.key != null) {
                allowedSupplyItem.setKey(allowedSupplyItemDTO.key);
            }
            if(allowedSupplyItemDTO.operator != null) {
                allowedSupplyItem.setOperator(allowedSupplyItemDTO.operator);
            }
            if(allowedSupplyItemDTO.value != null) {
                allowedSupplyItem.setValue(allowedSupplyItemDTO.value.toAllowedSupplyItem());
            }
        }
        allowedSupplyRepository.save(allowedSupply);
    }
}
