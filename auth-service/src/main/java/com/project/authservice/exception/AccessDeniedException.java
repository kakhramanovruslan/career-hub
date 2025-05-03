package com.project.authservice.exception;

/**
 * Custom exception class for handling access denial situations.
 * This exception is thrown when a user is not authorized to perform an action.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructor for AccessDeniedException.
     * Initializes the exception with a custom message.
     *
     * @param message the detail message explaining the reason for the access denial
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
