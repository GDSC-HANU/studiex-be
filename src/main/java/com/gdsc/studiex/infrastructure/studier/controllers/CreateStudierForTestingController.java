package com.gdsc.studiex.infrastructure.studier.controllers;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.services.CreateStudierForTestingService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateStudierForTestingController {
    @Autowired
    private CreateStudierForTestingService createStudierForTestingService;

    @PostMapping("/studier/createForTesting")
    public ResponseEntity<?> createStudierForTesting(@RequestBody String body) {
        return ControllerHandler.handle(() ->
        {
            final CreateStudierForTestingService.CreateStudierForTestingInput input = CustomObjectMapper.deserialize(
                    body,
                    CreateStudierForTestingService.CreateStudierForTestingInput.class
            );
            final Id id = createStudierForTestingService.createStudierForTesting(input);
            return new ControllerHandler.Result("Success", null);
        });
    }

}
