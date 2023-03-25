package com.gdsc.studiex.domain.studier.models;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Builder
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
}
