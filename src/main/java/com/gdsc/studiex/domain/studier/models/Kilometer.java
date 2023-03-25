package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.PositiveNumber;

public class Kilometer extends PositiveNumber {
    public Kilometer(double value) throws InvalidInputException {
        super(value);
    }

    public static Kilometer fromMeter(double meter) {
        return new Kilometer(meter / 1000);
    }
}
