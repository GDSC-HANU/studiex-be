package com.gdsc.studiex.domain.pair.services;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.pair.models.PairRequestDTO;
import com.gdsc.studiex.domain.pair.repositories.PairRepository;
import com.gdsc.studiex.domain.pair.repositories.PairRequestRepository;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreatePairRequestService {
    private final PairRequestRepository pairRequestRepository;
    private final PairRepository pairRepository;
    private final StudierRepository studierRepository;

    public void createPairRequest(PairRequestDTO pairRequestDTO) throws BusinessLogicException {
        Studier studierToPair = studierRepository.findByStudierId(pairRequestDTO.getToStudierId());
        PairRequest existedPairRequest = pairRequestRepository.findPairRequest(
                List.of(pairRequestDTO.fromStudierId, pairRequestDTO.toStudierId)
        );
        Pair existedPair = pairRepository.findPair(
                List.of(pairRequestDTO.fromStudierId, pairRequestDTO.toStudierId)
        );
        if(studierToPair == null) {
            throw new BusinessLogicException("Cannot found this studier", "NOT_FOUND");
        }
        if(existedPair == null && existedPairRequest == null) {
            PairRequest pairRequest = PairRequest.newPairRequestBuilder()
                    .fromStudierId(pairRequestDTO.fromStudierId)
                    .toStudierId(pairRequestDTO.toStudierId)
                    .build();
            pairRequestRepository.save(pairRequest);
        }
    }
}
