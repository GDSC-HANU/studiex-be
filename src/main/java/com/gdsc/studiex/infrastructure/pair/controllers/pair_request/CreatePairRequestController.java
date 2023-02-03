package com.gdsc.studiex.infrastructure.pair.controllers.pair_request;


import com.gdsc.studiex.domain.pair.models.PairRequestDTO;
import com.gdsc.studiex.domain.pair.services.CreatePairRequestService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreatePairRequestController {
    private final CreatePairRequestService createPairRequestService;
    private final AuthorizeStudierService authorizeStudierService;

    @PostMapping("/pairRequest")
    public ResponseEntity<?> createPairRequest(@RequestHeader(name = "access-token", required = true) String accessToken,
            @RequestBody Id toStudierId) {
        return ControllerHandler.handle(() -> {
            final Id fromStudierId = authorizeStudierService.authorize(accessToken);
            createPairRequestService.createPairRequest(PairRequestDTO.builder()
                            .fromStudierId(fromStudierId)
                            .toStudierId(toStudierId)
                            .build());
            return new ControllerHandler.Result(
                    "Success",
                    null);
        });
    }
}
