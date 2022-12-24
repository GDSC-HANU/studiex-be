package com.gdsc.studiex.domain.share.exceptions;

public class InvalidInputException extends RuntimeBusinessLogicException {
    public InvalidInputException(String message) {
        super(message, "INVALID_INPUT");
    }
}
