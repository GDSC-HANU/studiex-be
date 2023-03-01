package com.gdsc.studiex.domain.studier.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StudierDTO {
    private String name;
    private Gender gender;
    private Integer yob;
    private Url avatar;

    public static StudierDTO fromStudier(Studier studier) {
        return StudierDTO.builder()
                .name(studier.getName())
                .avatar(studier.getAvatar())
                .gender(studier.getGender())
                .yob(studier.getYob())
                .build();
    }
}
