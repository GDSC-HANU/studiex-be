package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.allowed_supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyDTO;
import com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply.CreateAllowedSupplyService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
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
    public ResponseEntity<?> createAllowedSupply(@RequestBody AllowedSupplyDTO input) {
        return ControllerHandler.handle(() -> {
            final Id id = createAllowedSupplyService.createAllowedSupply(input);
            return new ControllerHandler.Result(
                    "Success",
                    id
            );
        });
    }
}
