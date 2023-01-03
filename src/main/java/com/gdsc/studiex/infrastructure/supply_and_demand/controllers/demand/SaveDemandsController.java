package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.services.demand.SaveDemandsService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaveDemandsController {
    @Autowired
    private SaveDemandsService saveDemandsService;
    @Autowired
    private AuthorizeStudierService authorizeStudierService;

    @PostMapping("/demand")
    public ResponseEntity<?> saveSupplies(@RequestHeader(name = "access-token") String accessToken,
                                          @RequestBody List<DemandsDTO.DemandDTO> body) {
        return ControllerHandler.handle(() -> {
            final Id studierId = authorizeStudierService.authorize(accessToken);
            saveDemandsService.saveDemands(studierId, body);
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }
}
