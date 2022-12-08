package com.gdsc.studyex.domain.share.exceptions;

public class BusinessLogicException extends Exception {
    public String code;

    public BusinessLogicException(String message, String code) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
