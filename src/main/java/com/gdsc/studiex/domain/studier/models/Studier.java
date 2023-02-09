package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Studier {
    private Id studierId;
    private String name;
    private Gender gender;
    private Integer yob;
    private Url avatar;

    private Studier() {
    }

    @Builder(builderMethodName = "newStudierBuilder", builderClassName = "NewStudierBuilder")
    public Studier(String name, Gender gender, Integer yob, Url avatar) {
        this.studierId = Id.generateRandom();
        this.name = name;
        this.gender = gender;
        this.yob = yob;
        this.avatar = avatar;
    }
}
