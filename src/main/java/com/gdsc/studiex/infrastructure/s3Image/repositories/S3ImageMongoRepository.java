package com.gdsc.studiex.infrastructure.s3Image.repositories;

import com.gdsc.studiex.domain.s3Image.models.S3Image;
import com.gdsc.studiex.domain.s3Image.repositories.S3ImageRepository;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class S3ImageMongoRepository implements S3ImageRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    private final String COLLECTION = "s3Images";

    @Override
    public void save(S3Image s3Image) {
        final Query query = Query.query(
                Criteria.where("imageId")
                        .is(s3Image.getImageId().toString())
        );
        final Update update = new Update();
        update.set("_id", s3Image.getImageId().toObjectId());
        update.set("imageId", s3Image.getImageId().toString());
        update.set("s3FilePath", s3Image.getS3FilePath());
        update.set("s3FileName", s3Image.getS3FileName());
        update.set("imageType", s3Image.getImageType().toString());
        update.set("privacyType", s3Image.getPrivacyType().toString());
        mongoTemplate.upsert(query, update, COLLECTION);
    }

    @Override
    public List<S3Image> getAllByIds(List<Id> imageIds) {
        final Query query = Query.query(
                Criteria.where("imageId")
                        .in(imageIds.stream().map(Id::toString).collect(Collectors.toList()))
        );
        final List<String> result = mongoTemplate.find(query, String.class, COLLECTION);
        return result.stream()
                .map(s -> CustomObjectMapper.deserialize(s, S3Image.class))
                .collect(Collectors.toList());
    }
}
