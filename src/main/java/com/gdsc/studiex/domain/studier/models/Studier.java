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
    private Set<Id> qualificationIds;
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
    public Studier(Id studierId, String name, Gender gender, Integer yob, Url avatar, Url facebookLink,
                   Set<Id> qualificationIds, Set<Id> personalityIds, Set<Id> likeIds, Set<Id> dislikeIds,
                   Coordinates coordinates, Set<Id> lifeGoalIds, Set<Id> learningStyleIds, Set<Id> majorIds) {
        this.studierId = Id.generateRandom();
        this.name = name;
        this.gender = gender;
        this.yob = yob;
        this.avatar = avatar;
        this.facebookLink = facebookLink;
        this.qualificationIds = qualificationIds;
        this.personalityIds = personalityIds;
        this.likeIds = likeIds;
        this.dislikeIds = dislikeIds;
        this.coordinates = coordinates;
        this.lifeGoalIds = lifeGoalIds;
        this.learningStyleIds = learningStyleIds;
        this.majorIds = majorIds;
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

    public void setQualificationIds(Set<Id> qualificationIds) {
        this.qualificationIds = qualificationIds;
    }

    public void setPersonalityIds(Set<Id> personalityIds) {
        this.personalityIds = personalityIds;
    }

    public void setLikeIds(Set<Id> likeIds) {
        this.likeIds = likeIds;
    }

    public void setDislikeIds(Set<Id> dislikeIds) {
        this.dislikeIds = dislikeIds;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setLifeGoalIds(Set<Id> lifeGoalIds) {
        this.lifeGoalIds = lifeGoalIds;
    }

    public void setLearningStyleIds(Set<Id> learningStyleIds) {
        this.learningStyleIds = learningStyleIds;
    }

    public void setMajorIds(Set<Id> majorIds) {
        this.majorIds = majorIds;
    }
}
