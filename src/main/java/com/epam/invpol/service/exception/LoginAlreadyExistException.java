package com.epam.invpol.service.exception;

public class LoginAlreadyExistException extends RuntimeException{

    public LoginAlreadyExistException() {
    }

    public LoginAlreadyExistException(String message) {
        super(message);
    }

    public LoginAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAlreadyExistException(Throwable cause) {
        super(cause);
    }
}
