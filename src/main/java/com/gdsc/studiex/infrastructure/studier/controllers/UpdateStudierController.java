package com.gdsc.studiex.infrastructure.studier.controllers;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StudierDTO;
import com.gdsc.studiex.domain.studier.services.UpdateStudierService;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UpdateStudierController {
    private final UpdateStudierService updateStudierService;
    private final AuthorizeStudierService authorizeStudierService;

    @PutMapping("/studier")
    public ResponseEntity<?> updateStudierProfile(@RequestHeader("access-token") String accessToken, @RequestBody String json) {
        return ControllerHandler.handle(() -> {
            Id studierId = authorizeStudierService.authorize(accessToken);
            StudierDTO studierDTO = CustomObjectMapper.deserialize(json, StudierDTO.class);
            updateStudierService.updateStudierProfile(UpdateStudierService.buildStudier(studierId, studierDTO));
            return new ControllerHandler.Result("Success", null);
        });
    }
}
