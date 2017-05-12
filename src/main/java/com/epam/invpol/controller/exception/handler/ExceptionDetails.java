package com.epam.invpol.controller.exception.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExceptionDetails {

    private List<String> errorMessages = new ArrayList<>();
    private Integer errorCode;

    ExceptionDetails() {
        super();
    }

    public ExceptionDetails(List<String> errorMessages, Integer errorCode) {
        this.errorMessages = errorMessages;
        this.errorCode = errorCode;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExceptionDetails that = (ExceptionDetails) o;
        return Objects.equals(errorMessages, that.errorMessages) &&
                Objects.equals(errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessages, errorCode);
    }

    @Override
    public String toString() {
        return "ExceptionDetails{" +
                "errorMessages='" + errorMessages + '\'' +
                ", errorCode=" + errorCode +
                '}';
    }
}

