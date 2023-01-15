package com.gdsc.studiex.infrastructure.studier_auth.controllers;

import com.gdsc.studiex.domain.studier_auth.services.LogInService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class LogInController {
    private final LogInService logInService;

    @PostMapping("/studierAuth/logIn")
    public ResponseEntity<?> logIn(@RequestHeader("fb-access-token") String fbAccessToken) {
        return ControllerHandler.handle(() -> {
            final String token = logInService.logIn(fbAccessToken);
            return new ControllerHandler.Result("Success", token);
        });
    }
}
