package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.DislikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DislikeMongoRepository extends StringEntitiesMongoRepository implements DislikeRepository {
    @Override
    protected String collectionName() {
        return "Studier.Dislikes";
    }
}
