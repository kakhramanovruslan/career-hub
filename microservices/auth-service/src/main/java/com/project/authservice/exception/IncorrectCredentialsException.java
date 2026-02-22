package com.project.authservice.exception;

/**
 * Custom exception class for handling incorrect credentials situations.
 * This exception is thrown when the credentials provided by the user are incorrect.
 */
public class IncorrectCredentialsException extends RuntimeException {

    /**
     * Constructor for IncorrectCredentialsException.
     * Initializes the exception with a custom message.
     *
     * @param message the detail message explaining why the credentials are incorrect
     */
    public IncorrectCredentialsException(String message) {
        super(message);
    }
}
