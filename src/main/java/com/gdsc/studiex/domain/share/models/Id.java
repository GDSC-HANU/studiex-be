package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Objects;

public class Id extends StringValueObject {

    public Id() {
        super();
    }

    public Id(String value) throws InvalidInputException {
        super(value);
    }

    @Override
    protected void validate() throws InvalidInputException {
        if (!ObjectId.isValid(value)) {
            throw new InvalidInputException("Invalid Id: '" + value);
        }
    }

    public static Id generateRandom() {
        return new Id(new ObjectId().toString());
    }

    public ObjectId toObjectId() {
        return new ObjectId(value);
    }

    public boolean equals(Id other) {
        return Objects.equals(this.value, other.value);
    }
}
