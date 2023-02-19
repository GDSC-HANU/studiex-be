package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.Collection;

public class Coordinates extends ArrayList<Double> {
    public Coordinates(int initialCapacity) {
        super(initialCapacity);
        validate();
    }

    public Coordinates() {
        validate();
    }

    public Coordinates(Collection<? extends Double> c) {
        super(c);
        validate();
    }

    public double latitude() {
        return get(0);
    }

    public double longtitude() {
        return get(1);
    }

    private void validate() {
        if (size() != 2)
            throw new InvalidInputException("Coordinates must be a list with two elements");
    }
}
