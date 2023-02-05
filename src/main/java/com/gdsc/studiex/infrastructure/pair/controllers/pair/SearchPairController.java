package com.gdsc.studiex.infrastructure.pair.controllers.pair;

import com.gdsc.studiex.domain.pair.models.PairDTO;
import com.gdsc.studiex.domain.pair.services.pair.SearchPairService;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.services.AuthorizeStudierService;
import com.gdsc.studiex.infrastructure.share.controllers.ControllerHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class SearchPairController {
    private final SearchPairService searchPairService;
    private final AuthorizeStudierService authorizeStudierService;

    @GetMapping("/pair")
    public ResponseEntity<?> getPairOfStudier(@RequestParam("page") int page, @RequestParam("perPage") int perPage){
        return ControllerHandler.handle(() -> {
            Id studierId = new Id("63c5003781e3051b2615cad2");
            List<PairDTO> result = searchPairService.findPairOfStudier(page, perPage, studierId);
            return new ControllerHandler.Result(
                    "Success",
                    result
            );
        });
    }
}
