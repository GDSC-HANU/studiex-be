package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class StudierPrivacy {
    private Id studierId;
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

    public static StudierPrivacy defaultStudierPrivacy(Id studierId) {
        return new StudierPrivacy(studierId,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PRIVATE,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC);
    }

    public static StudierPrivacy allPublic(Id studierId) {
        return new StudierPrivacy(studierId,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC,
                PrivacyType.PUBLIC);
    }

    public void setGender(PrivacyType gender) {
        this.gender = gender;
    }

    public void setYob(PrivacyType yob) {
        this.yob = yob;
    }

    public void setQualifications(PrivacyType qualifications) {
        this.qualifications = qualifications;
    }

    public void setPersonalities(PrivacyType personalities) {
        this.personalities = personalities;
    }

    public void setLikes(PrivacyType likes) {
        this.likes = likes;
    }

    public void setDislikes(PrivacyType dislikes) {
        this.dislikes = dislikes;
    }

    public void setLifeGoals(PrivacyType lifeGoals) {
        this.lifeGoals = lifeGoals;
    }

    public void setLearningStyles(PrivacyType learningStyles) {
        this.learningStyles = learningStyles;
    }

    public void setMajors(PrivacyType majors) {
        this.majors = majors;
    }
}
