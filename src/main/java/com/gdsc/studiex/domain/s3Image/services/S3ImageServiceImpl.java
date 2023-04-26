package com.gdsc.studiex.domain.s3Image.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class S3ImageServiceImpl implements S3ImageService {
    private final AmazonS3 s3Client;

    @Value("${s3.bucket_name}")
    private final String s3Bucket = "dds-gat";

    @Override
    public void uploadImageToS3(File image, String s3FilePath, String s3FileName) throws BusinessLogicException {
        String imageDirection = buildS3ImageDirection(s3FilePath, s3FileName);
        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(s3Bucket, imageDirection, image);
            s3Client.putObject(putObjectRequest);
        } catch (AmazonServiceException e){
            throw new BusinessLogicException("S3 not process", "NOT_PROCESS");
        } catch (SdkClientException e){
            throw new BusinessLogicException("aws sdk not found", "NOT_FOUND");
        }
    }

    @Override
    public ByteArrayOutputStream getS3Image(String s3FilePath, String s3FileName) throws BusinessLogicException {
        S3Object imageObject = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            imageObject = getImageObject(buildS3ImageDirection(s3FilePath, s3FileName));
            S3ObjectInputStream s3ObjectInputStream = imageObject.getObjectContent();
            byte[] readBuff = new byte[1024];
            int readLen = 0;
            while((readLen = s3ObjectInputStream.read(readBuff)) > 0) {
                byteArrayOutputStream.write(readBuff, 0, readLen);
            }
            s3ObjectInputStream.close();
        } catch (AmazonServiceException | IOException e) {
            throw new BusinessLogicException("Cannot read image content", "READ_FAILED");
        } finally {
            closeObject(imageObject);
        }
        return byteArrayOutputStream;
    }

    private S3Object getImageObject(String imageDirection) {
        return s3Client.getObject(s3Bucket, imageDirection);
    }

    private String buildS3ImageDirection(String s3FilePath, String s3FileName) {
        return String.format("%s/%s", s3FilePath, s3FileName);
    }

    private void closeObject(S3Object s3Object) throws BusinessLogicException {
        try {
            if (Objects.nonNull(s3Object)) {
                s3Object.close();
            }
        } catch (IOException e) {
            throw new BusinessLogicException("Cannot close s3 object", "CLOSE_FAILED");
        }
    }
}
