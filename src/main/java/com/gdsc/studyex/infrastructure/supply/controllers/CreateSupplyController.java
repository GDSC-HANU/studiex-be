package com.gdsc.studyex.infrastructure.supply.controllers;

import com.gdsc.studyex.domain.supplyAndDemand.services.supply.CreateSupplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateSupplyController {
    private final CreateSupplyService createSupplyService;

    public static class Input {
        public String studierId;
        public String allowedSupplyId;
    }

    public void createSupply() {
        //unimplement
    }
}
