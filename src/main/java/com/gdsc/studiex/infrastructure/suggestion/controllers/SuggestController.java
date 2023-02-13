package com.gdsc.studiex.infrastructure.suggestion.controllers;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.suggestion.models.SuggestorResult;
import com.gdsc.studiex.domain.suggestion.models.SuggestorResultDTO;
import com.gdsc.studiex.domain.suggestion.services.SuggestService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SuggestController {
    @Autowired
    private SuggestService suggestService;
    @Autowired
    private AuthorizeStudierService authorizeStudierService;

    @GetMapping("/suggest")
    public ResponseEntity<?> suggest(@RequestHeader(name = "access-token", required = true) String accessToken,
                                     @RequestParam(name = "limit", required = true) int limit) throws BusinessLogicException {
        return ControllerHandler.handle(() -> {
            final Id id = authorizeStudierService.authorize(accessToken);
            final List<SuggestorResultDTO> suggestorResults = suggestService.suggest(id, limit);
            return new ControllerHandler.Result("Success", suggestorResults);
        });
    }
}
