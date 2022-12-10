package com.gdsc.studyex.domain.share.models;

import com.gdsc.studyex.domain.share.exceptions.InvalidInputException;
import org.bson.types.ObjectId;

import java.util.Objects;

public class Id {
    private String value;

    private Id(String value) {
        this.value = value;
    }

    public static Id parse(String value) throws InvalidInputException {
        if (!ObjectId.isValid(value)) {
            throw new InvalidInputException("Invalid Id: '" + value + "'.");
        }
        return new Id(value);
    }

    public static Id generateRandom() {
        try {
            return new Id(new ObjectId().toString());
        } catch (Exception e) {
            // cannot reach
            return null;
        }
    }

    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id id = (Id) o;
        return Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
