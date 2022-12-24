package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.supply_and_demand.services.supply.SaveSuppliesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaveSuppliesController {
    @Autowired
    private SaveSuppliesService saveSuppliesService;
    @Autowired
    private AuthorizeStudierService authorizeStudierService;

    @PostMapping("/supply")
    public void saveSupplies(@RequestHeader String accessToken,
                             @RequestBody SaveSuppliesService.InputSupplies body) {
        final Id studierId = authorizeStudierService.authorize(accessToken);
        saveSuppliesService.saveSupplies(studierId, body);
    }
}
