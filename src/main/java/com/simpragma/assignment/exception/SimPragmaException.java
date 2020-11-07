package com.simpragma.assignment.exception;

public class SimPragmaException extends RuntimeException {

    public SimPragmaException() {
    }

    public SimPragmaException(final String message) {
        super(message);
    }

    public SimPragmaException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
