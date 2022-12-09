package com.gdsc.studyex.domain.supply.services.supply;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supply.models.Supply;
import com.gdsc.studyex.domain.supply.models.SupplyItem;
import com.gdsc.studyex.infrastructure.supply.repositories.SupplyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
