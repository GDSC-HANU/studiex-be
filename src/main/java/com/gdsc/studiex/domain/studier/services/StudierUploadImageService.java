package com.gdsc.studiex.domain.studier.services;

import com.gdsc.studiex.domain.s3Image.models.S3Image;
import com.gdsc.studiex.domain.s3Image.repositories.S3ImageRepository;
import com.gdsc.studiex.domain.s3Image.services.S3ImageService;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.models.StudierUploadImageDTO;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class StudierUploadImageService {
    private final StudierRepository studierRepository;
    private final S3ImageService imageToS3Service;
    private final S3ImageRepository s3ImageRepository;

    //todo: will rename when own S3 aws
    private final String FIlE_PATH = "hotfix_flickr_upload_limit";
    private final String IMAGE_FORMAT = "jpg";

    public Id uploadImage(Id studierId, StudierUploadImageDTO dto) throws BusinessLogicException {
        try {
            Studier studier = studierRepository.findByStudierId(studierId);

            String fileName = buildFileName(studierId);
            URL urlObject = new URL(dto.getUrl());
            File fileImage = new File(fileName);
            BufferedImage image = ImageIO.read(urlObject);
            ImageIO.write(image, IMAGE_FORMAT, fileImage);
            imageToS3Service.uploadImageToS3(fileImage, FIlE_PATH, fileName);

            S3Image s3Image = S3Image.newS3ImageBuilder()
                    .s3FilePath(FIlE_PATH)
                    .s3FileName(fileName)
                    .imageType(dto.getImageType())
                    .privacyType(dto.getPrivacyType())
                    .build();
            s3ImageRepository.save(s3Image);

            Set<Id> studierImages = studier.getImageIds();
            if(studierImages != null) {
                studierImages.add(s3Image.getImageId());
                studier.setImageIds(studierImages);
            } else {
                studier.setImageIds(Set.of(s3Image.getImageId()));
            }
            studierRepository.save(studier);
            return s3Image.getImageId();
        } catch (Exception e) {
            throw new BusinessLogicException("Cannot Upload Image", "UPLOAD_FAILED");
        }
    }

    private String buildFileName(Id studierId) {
        long dateTime = ZonedDateTime.now().toInstant().toEpochMilli();
        return String.format("%s_%s", studierId.toString(), dateTime);
    }
}
