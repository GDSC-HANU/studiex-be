package com.gdsc.studiex.infrastructure.pair.controllers.pair_request;

import com.gdsc.studiex.domain.pair.models.PairRequestDTO;
import com.gdsc.studiex.domain.pair.services.SearchPairRequestService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/pairRequest")
    public ResponseEntity<?> findPairRequestOfStudier(@RequestParam("pageInt") int pageInt,
                                                      @RequestParam("limit") int limit
                                                      ) {
        return ControllerHandler.handle(() -> {
            Id studierId = new Id("63c5003781e3051b2615cad2");
            List<PairRequestDTO> result = searchPairRequestService.findPairRequestOfStudier(pageInt, limit, studierId);
            return new ControllerHandler.Result(
                    "success",
                    result
            );
        });
    }
}
