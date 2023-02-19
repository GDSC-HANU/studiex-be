package com.gdsc.studiex.infrastructure.studier.repositories;

import com.gdsc.studiex.domain.studier.repositories.LikeRepository;
import org.springframework.stereotype.Repository;

@Repository
public class LikeMongoRepository extends StringEntityMongoRepository implements LikeRepository {
    @Override
    protected String collectionName() {
        return "Studier.Likes";
    }
}
