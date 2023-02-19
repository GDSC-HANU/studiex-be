package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.MajorRepository;
import com.gdsc.studiex.domain.studier.repositories.StringEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MajorMongoRepository extends StringEntityMongoRepository implements MajorRepository {
    @Override
    protected String collectionName() {
        return "Studier.Majors";
    }
}
