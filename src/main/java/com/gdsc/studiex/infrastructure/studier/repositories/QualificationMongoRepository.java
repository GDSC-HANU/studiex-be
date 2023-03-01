package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.QualificationRepository;
import org.springframework.stereotype.Repository;

@Repository
public class QualificationMongoRepository extends StringEntitiesMongoRepository implements QualificationRepository {
    @Override
    protected String collectionName() {
        return "Studier.Qualifications";
    }
}
