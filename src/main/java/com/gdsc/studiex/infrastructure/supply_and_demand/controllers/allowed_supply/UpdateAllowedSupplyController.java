package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.allowed_supply;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupplyDTO;
import com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply.UpdateAllowedSupplyService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateAllowedSupplyController {
    @Autowired
    private UpdateAllowedSupplyService updateAllowedSupplyService;

    @PutMapping("/supplyAndDemand/allowedSupply")
    public ResponseEntity<?> updateAllowedSupply(@RequestBody String json) {
        return ControllerHandler.handle(() -> {
            final AllowedSupplyDTO body = CustomObjectMapper.deserialize(json, AllowedSupplyDTO.class);
            updateAllowedSupplyService.updateAllowedSupply(body);
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }
}
