package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.LifeGoalRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LifeGoalMongoRepository extends StringEntityMongoRepository implements LifeGoalRepository {
    @Override
    protected String collectionName() {
        return "Studier.LifeGoals";
    }
}
