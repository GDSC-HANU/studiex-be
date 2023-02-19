package com.gdsc.studiex.infrastructure.pair.controllers.pair_request;

import com.gdsc.studiex.domain.pair.services.pair_request.HandlePairRequestService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HandlePairRequestController {
    private final HandlePairRequestService handlePairRequestService;
    private final AuthorizeStudierService authorizeStudierService;

    @DeleteMapping("/pair/pairRequest")
    public ResponseEntity<?> handlePairRequest(@RequestHeader("access-token") String accessToken, @RequestBody String json) {
        return ControllerHandler.handle(() -> {
            Id studierId = authorizeStudierService.authorize(accessToken);
            HandlePairRequestService.HandlePairRequestInput input = CustomObjectMapper.deserialize(json, HandlePairRequestService.HandlePairRequestInput.class);
            handlePairRequestService.handlePairRequest(input, studierId);
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }
}
