package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.StringValueObject;

public class Url extends StringValueObject {
    public Url(String value) throws InvalidInputException {
        super(value);
    }

    public Url(String value, String fieldName) throws InvalidInputException {
        super(value, fieldName);
    }

    @Override
    protected void validate() throws InvalidInputException {
    }
}
