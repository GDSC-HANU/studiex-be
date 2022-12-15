package com.gdsc.studyex.infrastructure.supply_and_demand.controllers.supply;

import com.gdsc.studyex.domain.supply_and_demand.services.supply.SaveSupplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateSupplyController {
    private final SaveSupplyService saveSupplyService;

    public static class Input {
        public String studierId;
        public String allowedSupplyId;
    }

    public void createSupply() {
        //unimplement
    }
}
