package com.gdsc.studiex.infrastructure.studier.controllers;

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

    @PostMapping("/admin/strings")
    public ResponseEntity<?> createStringEntities(@RequestBody Map<String, List<String>> inputMap, @RequestParam String type) {
        return ControllerHandler.handle(() ->
        {
            createStringEntitiesService.createStringEntities(inputMap.get("strings"), type);
            return new ControllerHandler.Result("Success", null);
        });
    }
}
