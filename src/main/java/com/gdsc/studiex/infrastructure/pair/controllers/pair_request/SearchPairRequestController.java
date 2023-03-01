package com.gdsc.studiex.infrastructure.pair.controllers.pair_request;

import com.gdsc.studiex.domain.pair.models.PairRequestDTO;
import com.gdsc.studiex.domain.pair.services.pair_request.SearchPairRequestService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SearchPairRequestController {
    private final SearchPairRequestService searchPairRequestService;
    private final AuthorizeStudierService authorizeStudierService;

    @GetMapping("/pair/pairRequest")
    public ResponseEntity<?> findPairRequestOfStudier(@RequestParam("page") int page,
                                                      @RequestParam("perPage") int perPage,
                                                      @RequestHeader("access-token") String accessToken) {
        return ControllerHandler.handle(() -> {
            Id studierId = authorizeStudierService.authorize(accessToken);
            List<PairRequestDTO> result = searchPairRequestService.findPairRequestOfStudier(page, perPage, studierId);
            return new ControllerHandler.Result(
                    "success",
                    result
            );
        });
    }
}
