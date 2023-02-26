package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.CustomSupplyItem;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SupplyPriority;
import com.gdsc.studiex.domain.supply_and_demand.services.supply.UpdateSuppliesService;
import com.gdsc.studiex.infrastructure.share.controllers.Socket;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UpdateSuppliesController {
    private final AuthorizeStudierService authorizeStudierService;
    private final UpdateSuppliesService updateSuppliesService;
    private final Socket socket;

    public static class Input {
        public String accessToken;
        public String supplyId;
        public String subjectName;
        public List<SuppliesDTO.SupplyItemDTO> supplyItems;
        public boolean active;
        public SupplyPriority priority;
        public List<CustomSupplyItem> customSupplyItems;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void updateSupply() {
        socket.addEventListener("SUPPLIES.UPDATE_SUPPLY", String.class, (socketIOClient, s, ackRequest) -> {
            Input input = CustomObjectMapper.instance().readValue(
                    s,
                    new com.fasterxml.jackson.core.type.TypeReference<Input>() {
                    }
            );
            Id studierId = authorizeStudierService.authorize(input.accessToken);
            updateSuppliesService.updateSupply(
                    studierId,
                    new Id(input.supplyId),
                    SuppliesDTO.SupplyDTO.builder()
                            .subjectName(input.subjectName)
                            .supplyItems(input.supplyItems)
                            .active(input.active)
                            .priority(input.priority)
                            .customSupplyItems(input.customSupplyItems)
                            .build()
            );
            socketIOClient.sendEvent("SUPPLIES.UPDATE_SUPPLY", "Update successfully");
        });
    }
}
