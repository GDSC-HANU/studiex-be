package com.gdsc.studiex.infrastructure.share.controllers;

import com.gdsc.studiex.domain.share.exceptions.BusinessLogicException;
import com.gdsc.studiex.domain.share.exceptions.RuntimeBusinessLogicException;
import com.gdsc.studiex.domain.share.exceptions.UnauthorizedException;
import com.gdsc.studiex.infrastructure.share.object_mapper.CustomObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerHandler {

    public static class Result {
        private String message;
        private Object data;

        public Result(String message, Object data) {
            this.message = message;
            this.data = data;
        }
    }

    @FunctionalInterface
    public static interface Runnable {
        public Result run() throws Throwable;
    }

    public static ResponseEntity<?> handle(Runnable runner) {
        try {
            final Result result = runner.run();
            final ResponseBody responseBody = new ResponseBody(result.message, result.data);
            final Object body = CustomObjectMapper.convertObjectClass(responseBody, Object.class);
            return new ResponseEntity<>(
                    body,
                    HttpStatus.OK
            );
        } catch (Throwable e) {
            if (e instanceof BusinessLogicException) {
                HttpStatus status = HttpStatus.BAD_REQUEST;
                if (e instanceof UnauthorizedException)
                    status = HttpStatus.UNAUTHORIZED;
                return new ResponseEntity<>(
                        new ResponseBody(e.getMessage(), ((BusinessLogicException) e).getCode(), null),
                        status
                );
            } else if (e instanceof RuntimeBusinessLogicException) {
                return new ResponseEntity<>(
                        new ResponseBody(e.getMessage(), ((RuntimeBusinessLogicException) e).getCode(), null),
                        HttpStatus.BAD_REQUEST
                );
            }
            e.printStackTrace();
            return new ResponseEntity<>(new ResponseBody(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(RuntimeBusinessLogicException.class)
    public ResponseEntity<?> handleRuntimeBusinessLogicError(RuntimeBusinessLogicException e) {
        return new ResponseEntity<>(
                new ResponseBody(e.getMessage(), ((RuntimeBusinessLogicException) e).getCode(), null),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<?> handleBusinessLogicError(BusinessLogicException e) {
        return new ResponseEntity<>(
                new ResponseBody(e.getMessage(), ((BusinessLogicException) e).getCode(), null),
                HttpStatus.BAD_REQUEST
        );
    }
}
