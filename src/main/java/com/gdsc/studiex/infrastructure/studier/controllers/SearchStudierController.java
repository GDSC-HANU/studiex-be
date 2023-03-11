package com.gdsc.studiex.infrastructure.studier.controllers;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.studier.services.SearchStudierService;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SearchStudierController {

    private AuthorizeStudierService authorizeStudierService;
    private final SearchStudierService searchStudierService;

    @GetMapping("/studier")
    public ResponseEntity<?> getStudier(@RequestHeader("access-token") String accessToken, @RequestParam("studierId") String studierId) {
        return ControllerHandler.handle(() ->
        {
            Id currentStudierId = authorizeStudierService.authorize(accessToken);
            return new ControllerHandler.Result("Success", searchStudierService.getStudier(new Id(studierId), currentStudierId));
        });
    }
}
