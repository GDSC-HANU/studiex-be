package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.supply_and_demand.models.supply.Supplies;
import com.gdsc.studiex.domain.supply_and_demand.repositories.SuppliesRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SuppliesMongoRepository implements SuppliesRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public void save(Supplies supplies) {
        final Query query = Query.query(
                Criteria
                        .where("_id")
                        .is(supplies.getStudierId().toString())
        );
        final Update update = new Update();
        update.set("_id", supplies.getStudierId().toObjectId());
        update.set("studierId", supplies.getStudierId().toObjectId());
        final Object supplyList = CustomObjectMapper.convertObjectClass(
                supplies.getSupplies(),
                Object.class
        );
        update.set("supplies", supplyList);
        mongoTemplate.upsert(
                query,
                update,
                Supplies.class
        );
    }
}
