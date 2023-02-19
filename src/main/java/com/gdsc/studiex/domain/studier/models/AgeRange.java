package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.time.ZonedDateTime;

public class AgeRange {
    private int from;
    private int to;

    public AgeRange(int from, int to) {
        this.from = from;
        this.to = to;
        validate();
    }

    private void validate() {
        if (from < 0 || to < 0)
            throw new InvalidInputException("Age range must be positive");
        if (from > to)
            throw new InvalidInputException("Invalid age range");
    }

    public int lowerboundYear() {
        return ZonedDateTime.now().getYear() - to;
    }

    public int upperboundYear() {
        return ZonedDateTime.now().getYear() - from;
    }
}
