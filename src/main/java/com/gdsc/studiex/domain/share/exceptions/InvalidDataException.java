package com.gdsc.studiex.domain.share.exceptions;

public class InvalidDataException extends RuntimeBusinessLogicException {
    public InvalidDataException(String message) {
        super(message, "INVALID_DATA_EXCEPTION");
    }
}
