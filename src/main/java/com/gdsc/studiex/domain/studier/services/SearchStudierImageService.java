package com.gdsc.studiex.domain.studier.services;


import com.gdsc.studiex.domain.s3Image.models.S3Image;
import com.gdsc.studiex.domain.s3Image.repositories.S3ImageRepository;
import com.gdsc.studiex.domain.s3Image.services.S3ImageService;
import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.models.Id;
import com.gdsc.studiex.domain.studier.models.PrivacyType;
import com.gdsc.studiex.domain.studier.models.Studier;
import com.gdsc.studiex.domain.studier.repositories.StudierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SearchStudierImageService {
    private final S3ImageService s3ImageService;
    private StudierRepository studierRepository;
    private final S3ImageRepository s3ImageRepository;

    public List<ByteArrayOutputStream> getAllImageOfStudier(Id studierId, Id currentStudierId) {
        Studier studier = studierRepository.findByStudierId(studierId);
        List<Id> imageIds = new ArrayList<>(studier.getImageIds());
        List<S3Image> s3Images = null;
        if(studierId != currentStudierId) {
            s3Images = s3ImageRepository.getAllByIds(imageIds).stream()
                    .filter(s3Image -> s3Image.getPrivacyType() == PrivacyType.PUBLIC)
                    .collect(Collectors.toList());
        } else {
            s3Images = s3ImageRepository.getAllByIds(imageIds);
        }
        return s3Images.stream()
                .map(s3Image -> {
                    try {
                        return s3ImageService.getS3Image(s3Image.getS3FilePath(), s3Image.getS3FileName());
                    } catch (BusinessLogicException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
}
