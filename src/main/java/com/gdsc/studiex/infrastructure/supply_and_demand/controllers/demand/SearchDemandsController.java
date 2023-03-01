package com.gdsc.studiex.infrastructure.supply_and_demand.controllers.demand;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.DemandsDTO;
import com.gdsc.studiex.domain.supply_and_demand.services.demand.SearchDemandsService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SearchDemandsController {
    private final SearchDemandsService searchDemandsService;

    @GetMapping("/supplyAndDemand/demand")
    public ResponseEntity<?> getDemands(@RequestParam("studierId") String studierId) {
        return ControllerHandler.handle(() -> {
            final List<DemandsDTO.DemandDTO> demandsDTO = searchDemandsService.searchDemands(new Id(studierId)).demands;
            return new ControllerHandler.Result(
                    "success",
                    demandsDTO
            );
        });
    }
}
