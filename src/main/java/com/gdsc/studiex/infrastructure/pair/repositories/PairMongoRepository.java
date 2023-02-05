package com.gdsc.studiex.infrastructure.pair.repositories;

import com.gdsc.studiex.domain.pair.models.Pair;
import com.gdsc.studiex.domain.pair.repositories.PairRepository;
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
public class PairMongoRepository implements PairRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTION = "pair";
    @Override
    public Pair findPair(List<Id> studierIds) {
        Query query = Query.query(
                Criteria.where("firstStudierId")
                        .in(studierIds)
                        .and("secondStudierId")
                        .in(studierIds)
        );
        String pair = mongoTemplate.findOne(query, String.class, COLLECTION);
        if(pair == null) {
            return null;
        }
        return CustomObjectMapper.deserialize(pair, Pair.class);
    }

    @Override
    public void save(Pair pair) {
        String id = pair.getFirstStudierId() + "#" + pair.getSecondStudierId();
        Query query = Query.query(
                Criteria.where("_id")
                        .is(id)
        );
        final Update update = new Update();
        update.set("_id", id);
        update.set("firstStudierId", pair.getFirstStudierId().toString());
        update.set("secondStudierId", pair.getSecondStudierId().toString());
        update.set("createdAt", pair.getCreatedAt().toString());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION);
    }
}
