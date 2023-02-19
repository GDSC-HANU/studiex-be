package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.PersonalityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PersonalityMongoRepository extends StringEntityMongoRepository implements PersonalityRepository {
    @Override
    protected String collectionName() {
        return "Studier.Personalities";
    }
}
