package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudierPrivacyDTO {
    private PrivacyType gender;
    private PrivacyType yob;
    private PrivacyType qualifications;
    private PrivacyType personalities;
    private PrivacyType likes;
    private PrivacyType dislikes;
    private PrivacyType coordinates;
    private PrivacyType lifeGoals;
    private PrivacyType learningStyles;
    private PrivacyType majors;

}
