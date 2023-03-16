package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.StringValueObject;

public class Language extends StringValueObject {
    public Language(String value) throws InvalidInputException {
        super(value);
    }
}
