package com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateAllowedSupplyService {
    private final AllowedSupplyRepository allowedSupplyRepository;

    public Id createAllowedSupply(AllowedSupplyDTO input) throws InvalidInputException {
        final AllowedSupply allowedSupply = AllowedSupply.newAllowedSupplyBuilder()
                .subjectName(input.subjectName)
                .build();
        for (AllowedSupplyDTO.AllowedSupplyItemDTO inputAllowedSupplyItem : input.allowedSupplyItems)
            allowedSupply.getAllowedSupplyItems().add(AllowedSupplyItem.newAllowedSupplyItemBuilder()
                    .key(inputAllowedSupplyItem.key)
                    .operator(inputAllowedSupplyItem.operator)
                    .value(inputAllowedSupplyItem.value.toAllowedSupplyItem())
                    .description(inputAllowedSupplyItem.description)
                    .required(inputAllowedSupplyItem.required)
                    .build());
        allowedSupplyRepository.save(allowedSupply);
        return allowedSupply.getId();
    }
}
