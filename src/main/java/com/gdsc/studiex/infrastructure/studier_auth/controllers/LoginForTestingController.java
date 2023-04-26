package com.gdsc.studiex.infrastructure.studier_auth.controllers;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.LoginForTestingService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginForTestingController {
    @Autowired
    private LoginForTestingService loginForTestingService;

    @PostMapping("/studierAuth/logIn/testing/{studierId}")
    public ResponseEntity<?> logIn(@PathVariable String studierId) {
        return ControllerHandler.handle(() -> {
            final LoginForTestingService.Output output = loginForTestingService.loginForTesting(new Id(studierId));
            return new ControllerHandler.Result("Success", output);
        });
    }
}
