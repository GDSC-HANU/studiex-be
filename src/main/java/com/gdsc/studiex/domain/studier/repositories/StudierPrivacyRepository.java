package com.gdsc.studiex.domain.studier.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.StudierPrivacy;

import java.util.List;

public interface StudierPrivacyRepository {
    public StudierPrivacy findByStudierId(Id studierId);
    public void save(StudierPrivacy studierPrivacy);

    public List<StudierPrivacy> findByStudierIds(List<Id> studierIds);

}
