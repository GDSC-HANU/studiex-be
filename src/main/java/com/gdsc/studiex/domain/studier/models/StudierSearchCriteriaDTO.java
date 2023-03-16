package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.PositiveNumber;
import lombok.Getter;

import java.util.Set;

@Getter
public class StudierSearchCriteriaDTO {
    private Set<String> qualifications;
    private AgeRange ageRange;
    private Gender gender;
    private Set<String> personalities;
    private Set<String> likes;
    private Set<String> dislikes;
    private PositiveNumber distance;
    private Set<String> lifeGoals;
    private Set<String> learningStyles;
    private Set<String> majors;
    private Set<Language> languagesForCommunication;
}
