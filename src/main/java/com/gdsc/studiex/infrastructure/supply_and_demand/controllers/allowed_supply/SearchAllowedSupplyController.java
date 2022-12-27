package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.allowed_supply;

import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;
import com.gdsc.studiex.domain.supply_and_demand.services.allowed_supply.SearchAllowedSupplyService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchAllowedSupplyController {
    @Autowired
    private SearchAllowedSupplyService searchAllowedSupplyService;

    @GetMapping(path = "/allowedSupply")
    public ResponseEntity<?> searchAllowedSupply(@RequestParam int page, @RequestParam int perPage) {
        return ControllerHandler.handle(() -> {
            final List<AllowedSupply> result = searchAllowedSupplyService.searchAllowedSupply(page, perPage);
            return new ControllerHandler.Result(
                    "Success",
                    result
            );
        });
    }
}
