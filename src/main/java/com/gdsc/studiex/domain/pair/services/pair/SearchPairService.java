package com.gdsc.studiex.domain.pair.services.pair;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.pair.models.PairDTO;
import com.gdsc.studiex.domain.pair.repositories.PairRepository;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchPairService {
    private final PairRepository pairRepository;

    public List<PairDTO> findPairOfStudier(int page, int perPage, Id studierId) {
        Pageable pageable = PageRequest.of(page, perPage);
        List<Pair> pair = pairRepository.findPairOfStudier(pageable, studierId);
        return pair.stream()
                .map(p -> PairDTO.builder()
                        .firstStudierId(p.getFirstStudierId())
                        .secondStudierId(p.getSecondStudierId())
                        .createdAt(p.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
}
