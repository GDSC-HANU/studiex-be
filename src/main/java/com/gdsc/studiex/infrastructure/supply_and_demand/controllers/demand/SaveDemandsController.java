package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.services.demand.SaveDemandsService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.controllers.Socket;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import com.gdsc.studiex.infrastructure.supply_and_demand.controllers.supply.SaveSuppliesController;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
    @Autowired
    private Socket socket;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class InputUpdate {
        public String accessToken;
        public List<DemandsDTO.DemandDTO> demandDTOs;
    }

    @PostMapping("/supplyAndDemand/demand")
    public ResponseEntity<?> saveSupplies(@RequestHeader(name = "access-token", required = true) String accessToken,
                                          @RequestBody String json) {
        return ControllerHandler.handle(() -> {
            final List<DemandsDTO.DemandDTO> body = CustomObjectMapper.instance().readValue(
                    json,
                    new com.fasterxml.jackson.core.type.TypeReference<List<DemandsDTO.DemandDTO>>() {
                    }
            );

            final Id studierId = authorizeStudierService.authorize(accessToken);
            saveDemandsService.saveDemands(studierId, body);
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void updateSupply() {
        socket.addEventListener("UPDATE_DEMANDS", String.class, (socketIOClient, s, ackRequest) -> {
            SaveDemandsController.InputUpdate input = CustomObjectMapper.instance().readValue(
                    s,
                    new com.fasterxml.jackson.core.type.TypeReference<SaveDemandsController.InputUpdate>() {
                    }
            );
            Id studierId = authorizeStudierService.authorize(input.accessToken);
            saveDemandsService.saveDemands(
                    studierId,
                    input.demandDTOs
            );
            socketIOClient.sendEvent("UPDATE_DEMANDS", "Update successfully");
        });
    }
}
