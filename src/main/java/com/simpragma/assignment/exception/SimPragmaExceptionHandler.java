package com.simpragma.assignment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class SimPragmaExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ApiErrorMessage> handleNotFound(final NotFoundException e) {
        ApiErrorMessage apiErrorMessage =
                createErrorMessage(NOT_FOUND, e);
        return ResponseEntity.status(NOT_FOUND).body(apiErrorMessage);
    }

    private ApiErrorMessage createErrorMessage(final HttpStatus status, final Exception e) {
        return new ApiErrorMessage(status.value(), status.getReasonPhrase(), e.getClass().getSimpleName(), e.getMessage());
    }

}