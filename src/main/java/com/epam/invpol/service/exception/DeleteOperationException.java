package com.epam.invpol.service.exception;

public class DeleteOperationException extends RuntimeException {

    public DeleteOperationException() {
        super();
    }

    public DeleteOperationException(String message) {
        super(message);
    }

    public DeleteOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteOperationException(Throwable cause) {
        super(cause);
    }
}
