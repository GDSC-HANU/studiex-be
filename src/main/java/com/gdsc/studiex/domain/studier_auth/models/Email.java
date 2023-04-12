package com.gdsc.studiex.domain.studier_auth.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.StringValueObject;

public class Email extends StringValueObject {
    public Email(String value) throws InvalidInputException {
        super(value);
    }

    public Email(String value, String fieldName) throws InvalidInputException {
        super(value, fieldName);
    }

    @Override
    protected void validate() throws InvalidInputException {
    }
}
