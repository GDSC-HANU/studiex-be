package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.supply_and_demand.services.supply.SearchSuppliesService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SearchSuppliesController {
    private final SearchSuppliesService searchSuppliesService;
    private final AuthorizeStudierService authorizeStudierService;

    @GetMapping("/supplies")
    public ResponseEntity<?> getSupplies(@RequestHeader("access-token") String accessToken) {
        return ControllerHandler.handle(() -> {
            final Id studierId = authorizeStudierService.authorize(accessToken);
            return new ControllerHandler.Result(
                    "success",
                    searchSuppliesService.getSupplies(studierId)
            );
        });
    }
}
