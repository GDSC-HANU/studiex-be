package com.gdsc.studiex.domain.s3Image.repositories;

import com.gdsc.studiex.domain.s3Image.models.S3Image;
import com.gdsc.studiex.domain.share.models.Id;

import java.util.List;
import java.util.Set;

public interface S3ImageRepository {
    public void save(S3Image s3Image);
    public List<S3Image> getAllByIds(List<Id> imageIds);
}
