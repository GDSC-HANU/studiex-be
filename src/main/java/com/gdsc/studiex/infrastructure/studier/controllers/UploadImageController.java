package com.gdsc.studiex.infrastructure.studier.controllers;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StudierUploadImageDTO;
import com.gdsc.studiex.domain.studier.services.StudierUploadImageService;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UploadImageController {
    private final StudierUploadImageService studierUploadImageService;
    private final AuthorizeStudierService authorizeStudierService;

    @PostMapping("/studier/uploadImage")
    public ResponseEntity<?> uploadImage(@RequestHeader("access-token") String accessToken,
                                         @RequestBody String json) {
        return ControllerHandler.handle(() -> {
            Id studierId = authorizeStudierService.authorize(accessToken);
            StudierUploadImageDTO dto = CustomObjectMapper.deserialize(
                    json,
                    StudierUploadImageDTO.class
            );
            Id imageId = studierUploadImageService.uploadImage(studierId, dto);
            return new ControllerHandler.Result("Success", imageId);
        });
    }
}
