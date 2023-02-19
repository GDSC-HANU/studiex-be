package com.gdsc.studiex.infrastructure.suggestion.controllers;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.domain.suggestion.models.PastSuggestion;
import com.gdsc.studiex.domain.suggestion.models.SuggestorResultDTO;
import com.gdsc.studiex.domain.suggestion.services.SearchPastSuggestionService;
import com.gdsc.studiex.domain.suggestion.services.SuggestService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearchPastSuggestionController {
    @Autowired
    private SearchPastSuggestionService searchPastSuggestionService;
    @Autowired
    private AuthorizeStudierService authorizeStudierService;

    @GetMapping("/suggestion/pastSuggestion")
    public ResponseEntity<?> pastSuggestion(@RequestHeader(name = "access-token", required = true) String accessToken)
            throws BusinessLogicException {
        return ControllerHandler.handle(() -> {
            final Id id = authorizeStudierService.authorize(accessToken);
            final PastSuggestion pastSuggestion = searchPastSuggestionService.findByStudierId(id);
            return new ControllerHandler.Result("Success", pastSuggestion);
        });
    }
}
