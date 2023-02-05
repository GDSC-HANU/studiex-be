package com.gdsc.studiex.domain.pair.services;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.pair.repositories.PairRepository;
import com.gdsc.studiex.domain.pair.repositories.PairRequestRepository;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HandlePairRequestService {
    private PairRequestRepository pairRequestRepository;
    private PairRepository pairRepository;
    private StudierRepository studierRepository;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class HandlePairRequestInput {
        public boolean accept;
        public String studierId;
    }

    public void handlePairRequest(HandlePairRequestInput input, Id loginStudierId) throws BusinessLogicException {
        Studier studier = studierRepository.findByStudierId(new Id(input.studierId));
        if(studier == null) {
            throw new BusinessLogicException("Not found this studier", "NOT_FOUND");
        }
        PairRequest pairRequest = pairRequestRepository.findOnePairRequestOfStudier(new Id(input.studierId), loginStudierId);
        if(pairRequest == null) {
            throw new BusinessLogicException("Not found this pair request", "NOT_FOUND");
        }
        if(input.accept) {
            pairRepository.save(
                    Pair.newPairBuilder()
                        .firstStudierId(loginStudierId)
                        .secondStudierId(new Id(input.studierId))
                        .build()
            );
        }
        pairRequestRepository.delete(pairRequest);
    }
}
