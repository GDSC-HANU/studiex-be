package com.gdsc.studiex.domain.supply_and_demand.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;

import java.util.List;

public interface AllowedSupplyRepository {
    public void save(AllowedSupply allowedSupply);

    public List<AllowedSupply> findBySubjectNames(List<String> subjectNames);

    public List<AllowedSupply> findPaging(int page, int perPage);

    public List<AllowedSupply> findByIds(List<Id> allowedSupplyIds);
}
