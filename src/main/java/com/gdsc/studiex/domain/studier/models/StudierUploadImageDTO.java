package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.s3Image.models.ImageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudierUploadImageDTO {
    private String url;
    private ImageType imageType;
    private PrivacyType privacyType;
}
