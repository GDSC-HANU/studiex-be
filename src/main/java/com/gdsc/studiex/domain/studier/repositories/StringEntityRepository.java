package com.gdsc.studiex.domain.studier.repositories;

import com.gdsc.studiex.domain.studier.models.StringEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface StringEntityRepository {
    public List<StringEntity> findByValues(Collection<String> values);
}
