package com.gdsc.studyex.domain.supplyAndDemand.services.allowedSupply;

import com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply.AllowedSupply;
import com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply.AllowedSupplyItem;
import com.gdsc.studyex.domain.supplyAndDemand.repositories.AllowedSupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CreateAllowedSupplyService {
    private final AllowedSupplyRepository allowedSupplyRepository;

    public static class Input {
        public String subjectName;
        public List<AllowedSupplyItem> allowedSupplyItems;
    }

    public AllowedSupply createAllowedSupply(Input input) {
        final AllowedSupply allowedSupply = AllowedSupply.newAllowedSupplyBuilder()
                .subjectName(input.subjectName)
                .allowedSupplyItems(input.allowedSupplyItems)
                .build();
        allowedSupplyRepository.save(allowedSupply);
        return allowedSupply;
    }
}
