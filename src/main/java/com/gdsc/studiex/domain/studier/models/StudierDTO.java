package com.gdsc.studiex.domain.studier.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
public class StudierDTO {
    private String name;
    private Gender gender;
    private Integer yob;
    private Url avatar;
    private Set<String> qualifications;
    private Set<String> personalities;
    private Set<String> likes;
    private Set<String> dislikes;
    private Coordinates coordinates;
    private Set<String> lifeGoals;
    private Set<String> learningStyles;
    private Set<String> majors;

    @Builder(builderMethodName = "withPrivacy", builderClassName = "WithPrivacyBuilder")
    public StudierDTO(String name,
                      Gender gender,
                      Integer yob,
                      Url avatar,
                      Set<String> qualifications,
                      Set<String> personalities,
                      Set<String> likes,
                      Set<String> dislikes,
                      Coordinates coordinates,
                      Set<String> lifeGoals,
                      Set<String> learningStyles,
                      Set<String> majors,
                      StudierPrivacy studierPrivacy) {
        this.name = name;
        this.gender = studierPrivacy.getGender().equals(PrivacyType.PUBLIC) ? null : gender;
        this.yob = studierPrivacy.getGender().equals(PrivacyType.PUBLIC) ? null : yob;
        this.avatar = avatar;
        this.qualifications = studierPrivacy.getQualifications().equals(PrivacyType.PUBLIC) ? null : qualifications;
        this.personalities = studierPrivacy.getPersonalities().equals(PrivacyType.PUBLIC) ? null : personalities;
        this.likes = studierPrivacy.getLikes().equals(PrivacyType.PUBLIC) ? null : likes;
        this.dislikes = studierPrivacy.getDislikes().equals(PrivacyType.PUBLIC) ? null : dislikes;
        this.coordinates = studierPrivacy.getCoordinates().equals(PrivacyType.PUBLIC) ? null : coordinates;
        this.lifeGoals = studierPrivacy.getLifeGoals().equals(PrivacyType.PUBLIC) ? null : lifeGoals;
        this.learningStyles = studierPrivacy.getLearningStyles().equals(PrivacyType.PUBLIC) ? null : learningStyles;
        this.majors = studierPrivacy.getMajors().equals(PrivacyType.PUBLIC) ? null : majors;
    }
}
