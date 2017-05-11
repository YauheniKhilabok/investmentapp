package com.epam.invpol.controller.exception.handler;

import com.epam.invpol.controller.exception.handler.logger.annotation.ErrorLoggable;
import com.epam.invpol.controller.exception.handler.logger.annotation.WarnLoggable;
import com.epam.invpol.service.exception.InvalidLimitException;
import com.epam.invpol.service.exception.DeleteOperationException;
import com.epam.invpol.service.exception.EntityAlreadyExistException;
import com.epam.invpol.service.exception.EntityNotFoundException;
import com.epam.invpol.service.exception.EmailAlreadyExistException;
import com.epam.invpol.service.exception.LoginAlreadyExistException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @WarnLoggable
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleDataNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errorMessages = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            errorMessages = formErrorMessage(bindingResult);
        }
        ExceptionDetails exceptionDetails = fillExceptionDetails(errorMessages, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    private static List<String> formErrorMessage(BindingResult bindingResult) {
        List<String> notValidErrorList = new ArrayList<>();
        bindingResult.getAllErrors().stream().filter(object -> object instanceof FieldError).forEach(object -> {
            FieldError fieldError = (FieldError) object;
            notValidErrorList.add(fieldError.getDefaultMessage());
        });
        return notValidErrorList;
    }

    @WarnLoggable
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleDomainNotFoundException(EntityNotFoundException e) {
        ExceptionDetails exceptionDetails;
        if (e.getErrorMessages().isEmpty()) {
            String errorMessage = "Requested data not found.";
            exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.NOT_FOUND.value());
        } else {
            exceptionDetails = fillExceptionDetails(e.getErrorMessages(), HttpStatus.NOT_FOUND.value());
        }
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @WarnLoggable
    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleEntityAlreadyExistException(EntityAlreadyExistException e) {
        String errorMessage = e.getMessage();
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @WarnLoggable
    @ExceptionHandler(DeleteOperationException.class)
    public ResponseEntity<ExceptionDetails> handleDeleteOperationException(DeleteOperationException e) {
        String errorMessage = "Removal is impossible, since other entities depend on this entity.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @WarnLoggable
    @ExceptionHandler(InvalidLimitException.class)
    public ResponseEntity<ExceptionDetails> handleInvalidLimitException(InvalidLimitException e) {
        String errorMessage = "Limit value is not allowed. The maximum value of limit is 100";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @WarnLoggable
    @ExceptionHandler(LoginAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleLoginAlreadyExistException(LoginAlreadyExistException e) {
        String errorMessage = e.getMessage();
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @WarnLoggable
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<ExceptionDetails> handleEmailAlreadyExistException(EmailAlreadyExistException e) {
        String errorMessage = e.getMessage();
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ErrorLoggable
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionDetails> handleConstraintViolationException(DataIntegrityViolationException e) {
        String errorMessage = "Removal is impossible, since other entities depend on this entity.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ErrorLoggable
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDetails> handleUriException(MethodArgumentTypeMismatchException e) {
        String errorMessage = "Invalid request. Check the input parameters. Requested data not found.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.NOT_FOUND);
    }

    @ErrorLoggable
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionDetails> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        String errorMessage = "The header should not be empty and content type should be application/json.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ErrorLoggable
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<String> handleMediaTypeNotSupportedException(HttpMediaTypeNotAcceptableException e) {
        String errorMessage = "Client does not support the data that have been returned from the server.";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }

    @ErrorLoggable
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDetails> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String errorMessage = "This method is not allowed.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.METHOD_NOT_ALLOWED.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ErrorLoggable
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handleMessageNotReadableException(HttpMessageNotReadableException e) {
        String errorMessage = "The body of the request can not be empty. The number of required parameters shall not exceed the set value.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ErrorLoggable
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ExceptionDetails> handleTransientPropertyValueException(IllegalStateException e) {
        String errorMessage = "Specify the entity ID to which this object belongs.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ErrorLoggable
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDetails> handleNoFoundHandlerException(NoHandlerFoundException e) {
        String errorMessage = "Bad request. No suitable handler.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }

    @ErrorLoggable
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDetails> handleDefaultException(Exception e) {
        String errorMessage = "Server error. Something is wrong.";
        ExceptionDetails exceptionDetails = fillExceptionDetails(createErrorMessages(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> createErrorMessages(String errorMessage) {
        List<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);
        return errorMessages;
    }

    private ExceptionDetails fillExceptionDetails(List<String> errorMessages, Integer errorCode) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setErrorMessages(errorMessages);
        exceptionDetails.setErrorCode(errorCode);
        return exceptionDetails;
    }
}
