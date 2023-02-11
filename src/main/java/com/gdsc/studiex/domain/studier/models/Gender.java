package com.gdsc.studiex.domain.studier.models;

import java.util.Objects;

public enum Gender {
    MALE, FEMALE, UNKNOWN;

    public static Gender of(String gender) {
        if(Objects.equals(gender, "male")) {
            return Gender.MALE;
        }
        if(Objects.equals(gender, "female")) {
            return Gender.FEMALE;
        }
        if(gender == null) {
            return Gender.UNKNOWN;
        }
        return Gender.UNKNOWN;
    }
}
