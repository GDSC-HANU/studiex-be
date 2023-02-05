package com.gdsc.studiex.domain.pair.repositories;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.share.models.Id;

import java.util.List;

public interface PairRepository {
    public Pair findPair(List<Id> studierIds);
    public void save(Pair pair);
}
