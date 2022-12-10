package com.gdsc.studyex.domain.supplyAndDemand.services.supply;

import com.gdsc.studyex.domain.supplyAndDemand.repositories.SupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateSupplyService {
    private final SupplyRepository supplyRepository;

    public static class InputCreateSupply {
        public String studierId;
        public String allowedSupplyId;
    }

    public void createSupply(InputCreateSupply input) {
        //todo: get allowed supply item to create supply
    }
}
