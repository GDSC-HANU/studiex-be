package com.gdsc.studiex.domain.studier_auth.models;

public class Email {
    private String value;

    public Email(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
