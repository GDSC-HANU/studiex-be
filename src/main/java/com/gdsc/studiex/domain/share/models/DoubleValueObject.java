package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.util.Objects;

public class DoubleValueObject {
    protected double value;

    public DoubleValueObject(double value) throws InvalidInputException {
        this.value = value;
        validate();
    }

    public DoubleValueObject(double value, String fieldName) throws InvalidInputException {
        try {
            new DoubleValueObject(value);
        } catch (InvalidInputException e) {
            throw new InvalidInputException("Invalid value for field: " + fieldName + ", error message: " + e.getMessage());
        }
    }

    protected void validate() throws InvalidInputException {
    }

    public double asDouble() {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerValueObject that = (IntegerValueObject) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
