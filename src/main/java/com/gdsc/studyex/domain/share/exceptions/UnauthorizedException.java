package com.gdsc.studyex.domain.share.exceptions;

public class UnauthorizedException extends BusinessLogicException {
    public UnauthorizedException() {
        super("Unauthorized", "UNAUTHORIZED");
    }

    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED");
    }
}
