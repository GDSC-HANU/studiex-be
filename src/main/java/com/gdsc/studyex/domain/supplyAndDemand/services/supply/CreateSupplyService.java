package com.gdsc.studyex.domain.supplyAndDemand.services.supply;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supplyAndDemand.models.supply.Supplies;
import com.gdsc.studyex.domain.supplyAndDemand.models.supply.Supply;
import com.gdsc.studyex.domain.supplyAndDemand.models.supply.SupplyItemOperator;
import com.gdsc.studyex.domain.supplyAndDemand.repositories.SupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CreateSupplyService {
    private final SupplyRepository supplyRepository;

    public static class InputSupplies {
        public List<InputSupply> supplies;
    }

    public static class InputSupply {
        public String subjectName;
        public List<InputSupplyItem> supplyItems;
        public boolean active;
    }

    public static class InputSupplyItem {
        public String key;
        public SupplyItemOperator supplyItemOperator;
        public Object value;
        public String description;
    }

    public void createSupply(Id studierId, InputSupplies input) {
        final List<Supply> supplyList = input.supplies
                .stream()
                .map(inputSupply -> buildSupply(inputSupply))
                .collect(Collectors.toList());
        final Supplies supplies = Supplies.newSuppliesBuilder()
                .studierId(studierId)
                .supplies(supplyList)
                .build();
    }

    public Supply buildSupply(InputSupply inputSupply) {
        return null;
    }
}
