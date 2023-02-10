package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.models.Id;
import lombok.Getter;

@Getter
public class Studier {
    private Id studierId;
    private String name;
    private Gender gender;
    private int yob;
    private Url avatar;
    private Url facebookLink;

    private Studier(Id studierId, String name, Gender gender, int yob, Url avatar, Url facebookLink) {
        this.studierId = studierId;
        this.name = name;
        this.gender = gender;
        this.yob = yob;
        this.avatar = avatar;
        this.facebookLink = facebookLink;
    }
    public static Studier createStudierWithoutId(String name, Gender gender, int yob, Url avatar, Url facebookLink) {
        return new Studier(Id.generateRandom(), name, gender, yob, avatar, facebookLink);
    }

    public static Studier createStudierWithId(Id studierId, String name, Gender gender, int yob, Url avatar, Url facebookLink) {
        return new Studier(studierId, name, gender, yob, avatar, facebookLink);
    }
}
