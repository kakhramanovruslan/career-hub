package com.project.authservice.exception;

/**
 * Custom exception class for handling scenarios where a user is not found.
 * This exception is thrown when an operation is attempted on a user
 * who does not exist in the system.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructor for UserNotFoundException.
     * Initializes the exception with a custom message.
     *
     * @param message the detail message explaining why the user is not found
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
