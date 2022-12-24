package com.gdsc.studiex.domain.share.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import org.bson.types.ObjectId;

public class Id extends StringValueObject {
    private String value;

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
}
