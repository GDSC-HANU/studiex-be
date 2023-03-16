package com.gdsc.studiex.domain.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StringEntities;
import com.gdsc.studiex.domain.studier.models.StringEntity;

import java.util.List;
import java.util.Set;

public interface StringEntitiesRepository {
    public StringEntities find();
    public List<StringEntity> findByIds(Set<Id> ids);
    public void save(StringEntities stringEntities);
}
