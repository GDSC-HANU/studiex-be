package com.gdsc.studiex.infrastructure.supply_and_demand.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.supply_and_demand.models.demand.Demands;
import com.gdsc.studiex.domain.supply_and_demand.repositories.DemandsRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DemandsMongoRepository implements DemandsRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTION = "demands";

    @Override
    public void save(Demands demands) {
        final Query query = Query.query(
                Criteria
                        .where("_id")
                        .is(demands.getStudierId().toString())
        );
        final Update update = new Update();
        update.set("_id", demands.getStudierId().toObjectId());
        update.set("studierId", demands.getStudierId().toString());
        final Object demandList = CustomObjectMapper.convertObjectClass(
                demands.getDemands(),
                Object.class
        );
        update.set("demands", demandList);
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION
        );
    }

    @Override
    public Demands findByStudierId(Id studierId) {
        final Query query = new Query(
                Criteria.where("studierId")
                        .is(studierId.toString())
        );
        final String demands = mongoTemplate.findOne(query, String.class, COLLECTION);
        return CustomObjectMapper.deserialize(demands, Demands.class);
    }

    @Override
    public List<Demands> findDemandsContains(List<Id> allowedSupplyIds) {
        // TODO
        return null;
    }
}