package com.gdsc.studiex.infrastructure.studier_auth.repositories;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier_auth.models.Account;
import com.gdsc.studiex.domain.studier_auth.repositories.AccountRepository;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMongoRepository implements AccountRepository {
    @Autowired
    private MongoTemplate mongoTemplate;

    private final String COLLECTION = "account";

    @Override
    public Account findByStudierId(Id studierId) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(studierId.toString())
        );
        final String account = mongoTemplate.findOne(query, String.class, COLLECTION);
        return CustomObjectMapper.deserialize(account, Account.class);
    }

    @Override
    public Account findByFacebookId(String facebookId) {
        Query query = Query.query(
                Criteria.where("facebookId")
                        .is(facebookId)
        );
        final String account = mongoTemplate.findOne(query, String.class, COLLECTION);
        return account == null ? null : CustomObjectMapper.deserialize(account, Account.class);
    }

    @Override
    public void save(Account account) {
        Query query = Query.query(
                Criteria.where("_id")
                        .is(account.getStudierId().toString())
        );
        Update update = new Update();
        update.set("_id", account.getStudierId().toObjectId());
        update.set("studierId", account.getStudierId().toString());
        update.set("facebookId", account.getFacebookId());
        update.set("email", account.getEmail().toString());
        update.set("password", account.getPassword());
        mongoTemplate.upsert(
                query,
                update,
                COLLECTION
        );
    }
}
