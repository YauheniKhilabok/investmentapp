package com.epam.invpol.service.exception;

public class InvalidLimitException extends RuntimeException {

    public InvalidLimitException() {
        super();
    }

    public InvalidLimitException(String message) {
        super(message);
    }

    public InvalidLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLimitException(Throwable cause) {
        super(cause);
    }
}
