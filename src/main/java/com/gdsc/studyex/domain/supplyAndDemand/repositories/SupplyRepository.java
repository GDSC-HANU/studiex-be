package com.gdsc.studyex.domain.supplyAndDemand.repositories;

import com.gdsc.studyex.domain.share.models.Id;
import com.gdsc.studyex.domain.supplyAndDemand.models.supply.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupplyRepository extends MongoRepository<Supply, Id> {
}
