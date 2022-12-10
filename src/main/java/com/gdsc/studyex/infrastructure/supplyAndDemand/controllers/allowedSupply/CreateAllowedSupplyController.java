package com.gdsc.studyex.infrastructure.supplyAndDemand.controllers.allowedSupply;

import com.gdsc.studyex.domain.supplyAndDemand.models.allowedSupply.AllowedSupply;
import com.gdsc.studyex.domain.supplyAndDemand.services.allowedSupply.CreateAllowedSupplyService;
import com.gdsc.studyex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreateAllowedSupplyController {
    private final CreateAllowedSupplyService createAllowedSupplyService;

    @PostMapping(path = "/allowedSupply")
    public ResponseEntity<?> createAllowedSupply(@RequestBody CreateAllowedSupplyService.Input input) {
        return ControllerHandler.handle(() -> {
            final AllowedSupply allowedSupply = createAllowedSupplyService.createAllowedSupply(input);
            return new ControllerHandler.Result(
                    "Success",
                    allowedSupply
            );
        });

    }
}
