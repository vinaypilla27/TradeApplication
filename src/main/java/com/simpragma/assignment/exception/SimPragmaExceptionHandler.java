package com.simpragma.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class SimPragmaExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiErrorMessage> handleNotFound(final NotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body(createErrorMessage(NOT_FOUND, e));
    }

    @ExceptionHandler(InvalidInputException.class)
    ResponseEntity<ApiErrorMessage> handleInvalidInput(final InvalidInputException e) {
        return ResponseEntity.status(BAD_REQUEST).body(createErrorMessage(BAD_REQUEST, e));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiErrorMessage> handleAllExceptions(final Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(createErrorMessage(INTERNAL_SERVER_ERROR, e));
    }

    private ApiErrorMessage createErrorMessage(final HttpStatus status, final Exception e) {
        return new ApiErrorMessage(status.value(),
                status.getReasonPhrase(), e.getClass().getSimpleName(), e.getMessage());
    }

}