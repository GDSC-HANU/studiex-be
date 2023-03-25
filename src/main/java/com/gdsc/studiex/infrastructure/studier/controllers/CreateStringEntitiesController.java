package com.gdsc.studiex.infrastructure.studier.controllers;

import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.repositories.StringEntitiesRepository;
import com.gdsc.studiex.domain.studier.services.CreateStringEntitiesService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class CreateStringEntitiesController {
    private CreateStringEntitiesService createStringEntitiesService;

    @PostMapping("/studier/strings")
    public ResponseEntity<?> createStringEntities(@RequestBody Body body, @RequestParam String type) {
        return ControllerHandler.handle(() ->
        {
            createStringEntitiesService.createStringEntities(body.strings, body.type);
            return new ControllerHandler.Result("Success", null);
        });
    }

    public static class Body {
        public List<String> strings;
        StringEntities.Type type;
    }
}
