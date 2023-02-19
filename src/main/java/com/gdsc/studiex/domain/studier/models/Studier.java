package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class Studier {
    private Id studierId;
    private String name;
    private Gender gender;
    private Integer yob;
    private Url avatar;
    private Url facebookLink;
    private Set<Id> qualificationId;
    private Set<Id> personalityIds;
    private Set<Id> likeIds;
    private Set<Id> dislikeIds;
    private Coordinates coordinates;
    private Set<Id> lifeGoalIds;
    private Set<Id> learningStyleIds;
    private Set<Id> majorIds;

    private Studier() {
    }

    @Builder(builderMethodName = "newStudierBuilder", builderClassName = "NewStudierBuilder")
    public Studier(String name, Gender gender, Integer yob, Url avatar, Url facebookLink) {
        this.studierId = Id.generateRandom();
        this.name = name;
        this.gender = gender;
        this.yob = yob;
        this.avatar = avatar;
        this.facebookLink = facebookLink;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYob(Integer yob) {
        this.yob = yob;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setAvatar(Url avatar) {
        this.avatar = avatar;
    }
}
