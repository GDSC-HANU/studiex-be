package com.gdsc.studyex.domain.share.exceptions;

public class DuplicatedException extends BusinessLogicException {
    public DuplicatedException(String message) {
        super(message, "DUPLICATED");
    }
}
