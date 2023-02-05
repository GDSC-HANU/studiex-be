package com.gdsc.studiex.infrastructure.pair.repositories;

import com.gdsc.studiex.domain.pair.models.PairRequest;
import com.gdsc.studiex.domain.pair.repositories.PairRequestRepository;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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
        update.set("fromStudierId", pairRequest.getFromStudierId().toString());
        update.set("toStudierId", pairRequest.getToStudierId().toString());
        update.set("createdAt", pairRequest.getCreatedAt().toString());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION);
    }

    @Override
    public List<PairRequest> findPairRequestOfStudier(Pageable pageable, Id studierId) {
        final Query query = Query.query(
                Criteria.where("toStudierId")
                        .is(studierId.toString())
        );
        query.with(pageable);
        List<String> result = mongoTemplate.find(query, String.class, COLLECTION);
        return result.stream()
                .map(s -> CustomObjectMapper.deserialize(s, PairRequest.class))
                .collect(Collectors.toList());
    }

    @Override
    public PairRequest findOnePairRequestOfStudier(Id fromStudierId, Id toStudierId) {
        final Query query = Query.query(
                Criteria.where("fromStudierId")
                        .is(fromStudierId.toString())
                        .and("toStudierId")
                        .is(toStudierId.toString())
        );
        String result = mongoTemplate.findOne(query, String.class, COLLECTION);
        return CustomObjectMapper.deserialize(result, PairRequest.class);
    }

    @Override
    public void delete(PairRequest pairRequest) {
        final Query query = Query.query(
                Criteria.where("fromStudierId")
                        .is(pairRequest.getFromStudierId().toString())
                        .and("toStudierId")
                        .is(pairRequest.getToStudierId().toString())
        );
        mongoTemplate.findAndRemove(query, String.class, COLLECTION);
    }
}
