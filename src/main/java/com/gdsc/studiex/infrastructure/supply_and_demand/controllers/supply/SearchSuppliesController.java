package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.supply;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.SuppliesDTO;
import com.gdsc.studiex.domain.supply_and_demand.services.supply.SearchSuppliesService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SearchSuppliesController {
    private final SearchSuppliesService searchSuppliesService;

    @GetMapping("/supplyAndDemand/supply")
    public ResponseEntity<?> getSupplies(@RequestParam("studierId") String studierId) {
        return ControllerHandler.handle(() -> {
            final List<SuppliesDTO.SupplyDTO> supplyDTOS = searchSuppliesService.getSupplies(new Id(studierId)).supplies;
            return new ControllerHandler.Result(
                    "success",
                    supplyDTOS
            );
        });
    }
}
