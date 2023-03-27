package com.gdsc.studiex.domain.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Language;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierSearchCriteria;

import java.util.List;
import java.util.Set;

public interface StudierRepository {
    public Studier findByStudierId(Id studierId);

    public void save(Studier studier);

    public List<Studier> searchByCriteria(Id studierId,
                                          StudierSearchCriteria studierSearchCriteria);

    public List<Studier> findByStudierIds(List<Id> studierIds);
}
