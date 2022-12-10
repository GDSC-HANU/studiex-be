package com.gdsc.studyex.infrastructure.supplyAndDemand.controllers.allowedSupply;

import com.gdsc.studyex.domain.supplyAndDemand.services.allowedSupply.CreateAllowedSupplyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateAllowedSupplyController {
    private final CreateAllowedSupplyService createAllowedSupplyService;


}
