package com.gdsc.studiex.infrastructure.share.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3AWSConfig {
    @Value("${s3.access.key}")
    private String accessKey;
    @Value("${s3.secret.key}")
    private String secretKey;

    @Bean
    public AmazonS3 s3Client(){
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.AP_SOUTHEAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
        return s3Client;
    }
}
