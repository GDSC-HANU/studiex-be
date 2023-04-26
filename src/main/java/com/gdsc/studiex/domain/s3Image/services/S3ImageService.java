package com.gdsc.studiex.domain.s3Image.services;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;

import java.io.ByteArrayOutputStream;
import java.io.File;

public interface S3ImageService {
    public void uploadImageToS3(File image, String s3FilePath, String s3FileName) throws BusinessLogicException;
    public ByteArrayOutputStream getS3Image(String s3FilePath, String s3FileName) throws BusinessLogicException;
}
