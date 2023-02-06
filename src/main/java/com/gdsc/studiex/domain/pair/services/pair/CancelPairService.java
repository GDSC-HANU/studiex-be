package com.gdsc.studiex.domain.pair.services.pair;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.pair.repositories.PairRepository;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CancelPairService {
    private final PairRepository pairRepository;

    public void cancelPair(Id loginStudierId, Id canceledStudierId) throws BusinessLogicException {
        boolean result = pairRepository.remove(loginStudierId, canceledStudierId);
        if(!result) {
            throw new BusinessLogicException("Could not found this pair", "NOT_FOUND");
        }
    }
}
