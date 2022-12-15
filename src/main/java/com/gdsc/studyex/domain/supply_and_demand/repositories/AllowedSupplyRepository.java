package com.gdsc.studyex.domain.supply_and_demand.repositories;

import com.gdsc.studyex.domain.supply_and_demand.models.allowed_supply.AllowedSupply;

import java.util.List;

public interface AllowedSupplyRepository {
    public void save(AllowedSupply allowedSupply);

    public List<AllowedSupply> findBySubjectNames(List<String> subjectNames);
}
