package com.gdsc.studyex.domain.supply_and_demand.services.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyItem;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.Supply;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.SupplyItemOperator;
import com.gdsc.studyex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studyex.domain.supply_and_demand.repositories.SupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaveSupplyService {
    private final SupplyRepository supplyRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;

    public static class InputSupplies {
        public List<InputSupply> supplies;
    }

    public static class InputSupply {
        public String subjectName;
        public List<InputSupplyItem> items;
        public boolean active;
    }

    public static class InputSupplyItem {
        public String key;
        public SupplyItemOperator operator;
        public Object value;
        public String description;
    }

    public void createSupply(Id studierId, InputSupplies input) throws InvalidInputException {
        final List<AllowedSupply> allowedSupplies = allowedSupplyRepository
                .findBySubjectNames(input.supplies.stream()
                        .map(supply -> supply.subjectName)
                        .collect(Collectors.toList()));
        final List<Supply> supplyList = new ArrayList<>();
        for (InputSupply inputSupply : input.supplies)
            supplyList.add(buildSupply(inputSupply, allowedSupplies));
        final Supplies supplies = Supplies.newSuppliesBuilder()
                .studierId(studierId)
                .supplies(supplyList)
                .build();
    }

    public Supply buildSupply(InputSupply inputSupply, List<AllowedSupply> allowedSupplies) throws InvalidInputException {
        AllowedSupply allowedSupply = null;
        for (AllowedSupply as : allowedSupplies)
            if (as.getSubjectName().equals(inputSupply.subjectName))
                allowedSupply = as;
        if (allowedSupply == null)
            throw new InvalidInputException("There are no Allowed Supply with subject name: " + inputSupply.subjectName);
        final Supply supply = Supply.newSupplyBuilder()
                .active(inputSupply.active)
                .allowedSupplyId(allowedSupply.getId())
                .build();
        for (InputSupplyItem inputSupplyItem : inputSupply.items) {
            final AllowedSupplyItem allowedSupplyItem = allowedSupply.findItemByKey(inputSupplyItem.key);
            if (allowedSupplyItem == null)
                throw new InvalidInputException("There are no Allowed Supply Item with key: " + inputSupplyItem.key);
            if (!allowedSupplyItem.canUse(inputSupplyItem.operator))
                throw new InvalidInputException(String.format("Cannot use operator %s for the key %s", inputSupplyItem.operator, inputSupplyItem.key));
            switch (allowedSupplyItem.getOperator()) {
                case MANY_OF:

                case ONE_OF:
                case BETWEEN:
            }
        }
        return null;
    }
}
