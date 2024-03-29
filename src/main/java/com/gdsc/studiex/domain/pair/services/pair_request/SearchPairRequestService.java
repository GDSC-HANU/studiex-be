package com.gdsc.studiex.domain.pair.services.pair_request;

import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.pair.models.PairRequestDTO;
import com.gdsc.studiex.domain.pair.repositories.PairRequestRepository;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchPairRequestService {
    private final PairRequestRepository pairRequestRepository;

    public List<PairRequestDTO> findPairRequestOfStudier(int page, int perPage, Id studier) {
        Pageable pageable = PageRequest.of(page, perPage, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PairRequest> pairRequests = pairRequestRepository.findPairRequestOfStudier(pageable, studier);
        return pairRequests.stream()
                .map(pairRequest -> PairRequestDTO.builder()
                        .fromStudierId(pairRequest.getFromStudierId())
                        .toStudierId(pairRequest.getToStudierId())
                        .createdAt(pairRequest.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
