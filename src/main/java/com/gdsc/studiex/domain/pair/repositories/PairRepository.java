package com.gdsc.studiex.domain.pair.repositories;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.share.models.Id;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PairRepository {
    public Pair findPair(List<Id> studierIds);
    public void save(Pair pair);
    public List<Pair> findPairOfStudier(Pageable pageable, Id studierId);
    public boolean remove(Id loginStudierId, Id canceledStudierId);
}
