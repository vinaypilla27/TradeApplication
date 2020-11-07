package com.simpragma.assignment.exception;

public class ApiErrorMessage {

    private final int status;
    private final String error;
    private final String exception;
    private final String message;

    public ApiErrorMessage(final int status,
            final String error,
            final String exception,
            final String message) {
        this.status = status;
        this.error = error;
        this.exception = exception;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }


}
