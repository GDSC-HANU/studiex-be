package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.LearningStyleRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LearningStyleMongoRepository extends StringEntityMongoRepository implements LearningStyleRepository {
    @Override
    protected String collectionName() {
        return "Studier.LearningStyles";
    }
}
