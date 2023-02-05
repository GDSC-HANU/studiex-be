package com.gdsc.studiex.infrastructure.pair.controllers.pair;

import com.gdsc.studiex.domain.pair.services.pair.CancelPairService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CancelPairController {
    private final CancelPairService cancelPairService;
    private final AuthorizeStudierService authorizeStudierService;

    @DeleteMapping("/pair")
    public ResponseEntity<?> cancelPair(@RequestHeader("access-token") String accessToken,
                                        @RequestBody String canceledStudierId) {
        return ControllerHandler.handle(() -> {
            Id loginStudierId = authorizeStudierService.authorize(accessToken);
            cancelPairService.cancelPair(loginStudierId, new Id(canceledStudierId));
            return new ControllerHandler.Result(
                    "Success",
                    null
            );
        });
    }
}
