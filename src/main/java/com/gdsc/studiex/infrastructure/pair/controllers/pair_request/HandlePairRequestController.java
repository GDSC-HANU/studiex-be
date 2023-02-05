package com.gdsc.studiex.infrastructure.pair.controllers.pair_request;

import com.gdsc.studiex.domain.pair.services.HandlePairRequestService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HandlePairRequestController {
    private final HandlePairRequestService handlePairRequestService;
    private final AuthorizeStudierService authorizeStudierService;

    @DeleteMapping("/pairRequest")
    public ResponseEntity<?> handlePairRequest(@RequestBody HandlePairRequestService.HandlePairRequestInput input) {
        return ControllerHandler.handle(() -> {
            Id studierId = new Id("63c5003781e3021b2615cad4");
            handlePairRequestService.handlePairRequest(input, studierId);
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }
}
