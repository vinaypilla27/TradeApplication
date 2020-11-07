package com.simpragma.assignment.exception;

public class InvalidInputException extends SimPragmaException {

    public InvalidInputException() {
    }

    public InvalidInputException(final String message) {
        super(message);
    }

    public InvalidInputException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
