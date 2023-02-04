package com.gdsc.studiex.domain.pair.services;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.pair.models.PairRequestDTO;
import com.gdsc.studiex.domain.pair.repositories.PairRepository;
import com.gdsc.studiex.domain.pair.repositories.PairRequestRepository;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CreatePairRequestService {
    private final PairRequestRepository pairRequestRepository;
    private final PairRepository pairRepository;

    public void createPairRequest(PairRequestDTO pairRequestDTO) {
        PairRequest existedPairRequest = pairRequestRepository.findPairRequest(
                List.of(pairRequestDTO.fromStudierId, pairRequestDTO.toStudierId)
        );
        Pair existedPair = pairRepository.findPair(
                List.of(pairRequestDTO.fromStudierId, pairRequestDTO.toStudierId)
        );
        if(existedPair == null && existedPairRequest == null) {
            PairRequest pairRequest = PairRequest.newPairRequestBuilder()
                    .fromStudierId(pairRequestDTO.fromStudierId)
                    .toStudierId(pairRequestDTO.toStudierId)
                    .build();
            pairRequestRepository.save(pairRequest);
        }
    }
}
