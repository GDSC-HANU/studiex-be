package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SuppliesMongoRepository implements SuppliesRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTION = "supplies";
    @Override
    public void save(Supplies supplies) {
        final Query query = Query.query(
                Criteria
                        .where("_id")
                        .is(supplies.getStudierId().toString())
        );
        final Update update = new Update();
        update.set("_id", supplies.getStudierId().toObjectId());
        update.set("studierId", supplies.getStudierId().toString());
        final Object supplyList = CustomObjectMapper.convertObjectClass(
                supplies.getSupplies(),
                Object.class
        );
        update.set("supplies", supplyList);
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION
        );
    }

    public Supplies findByStudierId(Id studierId) {
        final Query query = new Query(
                Criteria.where("_id")
                        .is(studierId.toString())
        );
        final String supplies = mongoTemplate.findOne(query, String.class, COLLECTION);
        if (supplies == null)
            return Supplies.emptySupplies(studierId);
        return CustomObjectMapper.deserialize(supplies, Supplies.class);
    }

    @Override
    public List<Supplies> findSuppliesContains(List<Id> allowedSupplyIds, List<Id> studierIds) {
        final Query query = new Query(
                Criteria.where("studierId")
                        .in(Id.toListString(studierIds))
                        .and("supplies")
                        .elemMatch(
                                Criteria.where("allowedSupplyId")
                                        .in(Id.toListString(allowedSupplyIds))
                        )
        );
        final List<String> result = mongoTemplate.find(query, String.class, COLLECTION);
        return result.stream()
                .map(str -> CustomObjectMapper.deserialize(str, Supplies.class))
                .collect(Collectors.toList());
    }
}
