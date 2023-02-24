package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.MajorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MajorMongoRepository extends StringEntitiesMongoRepository implements MajorRepository {
    @Override
    protected String collectionName() {
        return "Studier.Majors";
    }
}
