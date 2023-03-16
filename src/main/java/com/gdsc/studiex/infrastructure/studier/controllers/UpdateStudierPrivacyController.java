package com.gdsc.studiex.infrastructure.studier.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StudierPrivacy;
import com.gdsc.studiex.domain.studier.models.StudierPrivacyDTO;
import com.gdsc.studiex.domain.studier.services.UpdateStudierPrivacyService;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class UpdateStudierPrivacyController {
    private UpdateStudierPrivacyService updateStudierPrivacyService;
    private AuthorizeStudierService authorizeStudierService;

    @PutMapping("/studierPrivacy")
    public ResponseEntity<?> updateStudierPrivacy(@RequestHeader("access-token") String accessToken, @RequestBody String json) {
        return ControllerHandler.handle(() ->
        {
            Id studierId = authorizeStudierService.authorize(accessToken);
            StudierPrivacyDTO studierPrivacyDTO = CustomObjectMapper.deserialize(json, StudierPrivacyDTO.class);
            updateStudierPrivacyService.updateStudierPrivacy(studierId, studierPrivacyDTO);
            return new ControllerHandler.Result("Success", null);
        });
    }
}
