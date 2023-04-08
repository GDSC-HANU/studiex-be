package com.gdsc.studiex.domain.studier.models;

import com.gdsc.studiex.domain.share.exceptions.InvalidInputException;
import com.gdsc.studiex.domain.share.models.StringValueObject;

import java.util.Set;

public class Language extends StringValueObject {

    public static final String ENGLISH_VALUE = "English";
    public static final String VIETNAMESE_VALUE = "Vietnamese";
    public static final Set<String> validValues = Set.of(
            ENGLISH_VALUE,
            VIETNAMESE_VALUE
    );

    public Language(String value) throws InvalidInputException {
        super(value);
    }

    @Override
    protected void validate() throws InvalidInputException {
        if (!validValues.contains(value))
            throw new InvalidInputException("Invalid language: " + value);
    }

}
