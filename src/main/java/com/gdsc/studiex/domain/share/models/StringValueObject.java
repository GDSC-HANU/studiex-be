package com.gdsc.studiex.domain.share.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;

import java.util.Objects;

public class StringValueObject {
    @JsonProperty("value")
    protected String value;

    public StringValueObject() {}

    public StringValueObject(String value) throws InvalidInputException {
        this.value = value;
        validate();
    }

    public StringValueObject(String value, String fieldName) throws InvalidInputException {
        try {
            new StringValueObject(value);
        } catch (InvalidInputException e) {
            throw new InvalidInputException("Invalid value for field: " + fieldName + ", error message: " + e.getMessage());
        }
    }

    protected void validate() throws InvalidInputException {
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValueObject that = (StringValueObject) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
