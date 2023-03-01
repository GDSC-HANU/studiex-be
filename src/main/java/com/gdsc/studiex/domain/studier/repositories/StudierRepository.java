package com.gdsc.studiex.domain.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierSearchCriteria;

import java.util.List;

public interface StudierRepository {
    public Studier findByStudierId(Id studierId);

    public void save(Studier studier);

    public List<Studier> searchByCriteria(Id studierId, StudierSearchCriteria studierSearchCriteria);
}
