package com.gdsc.studiex.domain.pair.repositories;

import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.share.models.Id;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PairRequestRepository {
    public PairRequest findPairRequest(List<Id> studierIds);
    public void save(PairRequest pairRequest);
    public List<PairRequest> findPairRequestOfStudier(Pageable pageable, Id studierId);
}
