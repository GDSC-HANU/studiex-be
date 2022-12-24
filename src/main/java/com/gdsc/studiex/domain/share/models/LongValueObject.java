package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.util.Objects;

public class LongValueObject {
    protected long value;

    public LongValueObject(long value)  throws InvalidInputException {
        this.value  = value;
        validate();
    }

    public LongValueObject(long value, String fieldName) throws InvalidInputException {
        try {
            new LongValueObject(value);
        } catch (InvalidInputException e) {
            throw new InvalidInputException("Invalid value for field: " + fieldName + ", error message: " + e.getMessage());
        }
    }

    protected void validate() throws InvalidInputException {
    }

    public long asLong() {
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
        LongValueObject that = (LongValueObject) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
