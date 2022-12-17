package com.gdsc.studyex.domain.supply_and_demand.services.supply;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studyex.domain.supply_and_demand.models.supply.*;
import com.gdsc.studyex.domain.supply_and_demand.repositories.AllowedSupplyRepository;
import com.gdsc.studyex.domain.supply_and_demand.repositories.SuppliesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SaveSupplyService {
    private final SuppliesRepository suppliesRepository;
    private final AllowedSupplyRepository allowedSupplyRepository;

    public static class InputSupplies {
        public List<InputSupply> supplies;
    }

    public static class InputSupply {
        public String subjectName;
        public List<InputSupplyItem> items;
        public boolean active;
        public SupplyPriority priority;
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
        suppliesRepository.save(supplies);
    }

    public Supply buildSupply(InputSupply inputSupply, List<AllowedSupply> allowedSupplies) throws InvalidInputException {
        final AllowedSupply allowedSupply = findAllowedSupplyBySubjectName(allowedSupplies, inputSupply.subjectName);
        if (allowedSupply == null)
            throw new InvalidInputException("There are no Allowed Supply with subject name: " + inputSupply.subjectName);
        final List<SupplyItem> supplyItems = new ArrayList<>();
        for (InputSupplyItem inputSupplyItem : inputSupply.items)
            supplyItems.add(SupplyItem.fromAllowedSupplyBuilder()
                    .key(inputSupplyItem.key)
                    .operator(inputSupplyItem.operator)
                    .value(inputSupplyItem.value)
                    .description(inputSupplyItem.description)
                    .allowedSupply(allowedSupply)
                    .build());
        final Supply supply = Supply.fromAllowedSupplyBuilder()
                .allowedSupplyId(allowedSupply.getId())
                .items(supplyItems)
                .active(inputSupply.active)
                .priority(inputSupply.priority)
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