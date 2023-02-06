package com.gdsc.studiex.infrastructure.pair.repositories;

import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.pair.repositories.PairRequestRepository;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PairRequestMongoRepository implements PairRequestRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTION = "pairRequests";
    @Override
    public PairRequest findPairRequest(List<Id> studierIds) {
        final Query query = Query.query(
                Criteria.where("fromStudierId")
                        .in(studierIds)
                        .and("toStudierId")
                        .in(studierIds)
        );
        final String pairRequest = mongoTemplate.findOne(query, String.class, COLLECTION);
        if(pairRequest == null) {
            return null;
        }
        return CustomObjectMapper.deserialize(pairRequest, PairRequest.class);
    }

    @Override
    public void save(PairRequest pairRequest) {
        String id = pairRequest.getFromStudierId().toString() + "#" + pairRequest.getToStudierId().toString();
        final Query query = Query.query(
                Criteria.where("_id")
                        .is(id)
        );
        final Update update = new Update();
        update.set("_id", id);
        update.set("fromStudierId", pairRequest.getFromStudierId());
        update.set("toStudierId", pairRequest.getToStudierId());
        update.set("createdAt", pairRequest.getCreatedAt().toString());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION);
    }
}