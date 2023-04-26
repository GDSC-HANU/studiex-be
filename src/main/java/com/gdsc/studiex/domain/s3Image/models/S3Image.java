package com.gdsc.studiex.domain.s3Image.models;

import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.PrivacyType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class S3Image {
    private Id imageId;
    private String s3FilePath;
    private String s3FileName;
    private ImageType imageType;
    private PrivacyType privacyType;

    @Builder(builderMethodName = "newS3ImageBuilder", builderClassName = "NewS3ImageBuilder")
    public S3Image(String s3FilePath, String s3FileName, ImageType imageType, PrivacyType privacyType) {
        this.imageId = Id.generateRandom();
        this.s3FilePath = s3FilePath;
        this.s3FileName = s3FileName;
        this.imageType = imageType;
        this.privacyType = privacyType == null ? PrivacyType.PUBLIC : privacyType;
    }

}
