package com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItemValue;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyOperator;
import com.gdsc.studiex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreateAllowedSupplyService {
    private final AllowedSupplyRepository allowedSupplyRepository;

    public static class InputAllowedSupply {
        public String subjectName;
        public List<InputAllowedSupplyItem> items;
    }

    public static class InputAllowedSupplyItem {
        private String key;
        private AllowedSupplyOperator operator;
        private AllowedSupplyItemValue value;
        private String description;
    }

    public AllowedSupply createAllowedSupply(InputAllowedSupply input) throws InvalidInputException {
        final AllowedSupply allowedSupply = AllowedSupply.newAllowedSupplyBuilder()
                .subjectName(input.subjectName)
                .build();
        for (InputAllowedSupplyItem inputAllowedSupplyItem : input.items)
            allowedSupply.getAllowedSupplyItems().add(AllowedSupplyItem.newAllowedSupplyItemBuilder()
                    .key(inputAllowedSupplyItem.key)
                    .operator(inputAllowedSupplyItem.operator)
                    .value(inputAllowedSupplyItem.value)
                    .description(inputAllowedSupplyItem.description)
                    .build());
        allowedSupplyRepository.save(allowedSupply);
        return allowedSupply;
    }
}
