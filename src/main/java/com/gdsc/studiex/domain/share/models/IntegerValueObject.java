package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.util.Objects;

public class IntegerValueObject {
    protected int value;

    public IntegerValueObject(int value) throws InvalidInputException {
        this.value = value;
        validate();
    }

    public IntegerValueObject(int value, String fieldName) throws InvalidInputException {
        try {
            new IntegerValueObject(value);
        } catch (InvalidInputException e) {
            throw new InvalidInputException("Invalid value for field: " + fieldName + ", error message: " + e.getMessage());
        }
    }

    protected void validate() throws InvalidInputException {
    }

    public int asInt() {
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
