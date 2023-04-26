package com.gdsc.studiex.infrastructure.studier.controllers;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.services.SearchStudierImageService;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@AllArgsConstructor
public class SearchImageController {
    private final SearchStudierImageService searchStudierImageService;
    private final AuthorizeStudierService authorizeStudierService;

    @GetMapping("/studier/images")
    public void getAllImageOfStudier(HttpServletResponse response,
                                     @RequestHeader("access-token") String accessToken,
                                     @RequestParam("studierId") String studierId) {
        //todo: investigate way to return clean response
        ControllerHandler.handle(() -> {
            try {
                Id currentStudierId = authorizeStudierService.authorize(accessToken);
                List<ByteArrayOutputStream> result = searchStudierImageService.getAllImageOfStudier(
                        new Id(studierId),
                        currentStudierId
                );
                if(!result.isEmpty()) {
                    for(ByteArrayOutputStream baos : result) {
                        try {
                            baos.writeTo(response.getOutputStream());
                            response.setStatus(org.apache.http.HttpStatus.SC_OK);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        finally {
                            baos.close();
                        }
                    }
                } else {
                    response.setStatus(org.apache.http.HttpStatus.SC_BAD_REQUEST);
                }
            } catch (Exception ex) {
                response.setStatus(org.apache.http.HttpStatus.SC_BAD_REQUEST);
                try {
                    response.setContentType("text/plain; charset=UTF-8");
                    PrintWriter writer = response.getWriter();
                    writer.write(ex.getMessage());
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });
    }
}