package com.gdsc.studiex.domain.studier.models;

public class Url {
    private String value;

    public Url(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
