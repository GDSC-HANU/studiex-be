package com.gdsc.studyex.infrastructure.supply.repositories;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supply.models.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplyRepository extends MongoRepository<Supply, Id> {
}
