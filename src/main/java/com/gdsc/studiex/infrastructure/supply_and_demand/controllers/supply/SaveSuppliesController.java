package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.CustomSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyPriority;
import com.gdsc.studiex.domain.supply_and_demand.services.supply.SaveSuppliesService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.controllers.Socket;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
public class SaveSuppliesController {
    @Autowired
    private SaveSuppliesService saveSuppliesService;
    @Autowired
    private AuthorizeStudierService authorizeStudierService;
    @Autowired
    private Socket socket;

    @AllArgsConstructor
    @NoArgsConstructor
    public static class InputUpdate {
        public String accessToken;
        public List<SuppliesDTO.SupplyDTO> supplyDTOs;
    }

    @PostMapping("/supplyAndDemand/supply")
    public ResponseEntity<?> saveSupplies(@RequestHeader(name = "access-token", required = true) String accessToken,
                                          @RequestBody String json) {
        return ControllerHandler.handle(() -> {
            final List<SuppliesDTO.SupplyDTO> body = CustomObjectMapper.instance().readValue(
                    json,
                    new com.fasterxml.jackson.core.type.TypeReference<List<SuppliesDTO.SupplyDTO>>() {
                    }
            );
            final Id studierId = authorizeStudierService.authorize(accessToken);
            saveSuppliesService.saveSupplies(studierId, body);
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }

    @EventListener(ApplicationReadyEvent.class)
    public void updateSupply() {
        socket.addEventListener("UPDATE_SUPPLIES", String.class, (socketIOClient, s, ackRequest) -> {
            SaveSuppliesController.InputUpdate input = CustomObjectMapper.instance().readValue(
                    s,
                    new com.fasterxml.jackson.core.type.TypeReference<SaveSuppliesController.InputUpdate>() {
                    }
            );
            Id studierId = authorizeStudierService.authorize(input.accessToken);
            saveSuppliesService.saveSupplies(
                    studierId,
                    input.supplyDTOs
            );
            socketIOClient.sendEvent("UPDATE_SUPPLIES", "Update successfully");
        });
    }
}
