package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

public class PositiveNumber extends DoubleValueObject {
    public PositiveNumber(int value) throws InvalidInputException {
        super(value);
    }

    @Override
    protected void validate() throws InvalidInputException {
        if (value < 0)
            throw new InvalidInputException("Positive number must be greater than 0");
    }
}
