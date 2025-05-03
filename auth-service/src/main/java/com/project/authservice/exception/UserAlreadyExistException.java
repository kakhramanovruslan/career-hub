package com.project.authservice.exception;

/**
 * Custom exception class for handling scenarios where a user already exists.
 * This exception is thrown when an attempt to register a user is made,
 * but the user with the same details already exists in the system.
 */
public class UserAlreadyExistException extends RuntimeException {

    /**
     * Constructor for UserAlreadyExistException.
     * Initializes the exception with a custom message.
     *
     * @param message the detail message explaining why the user already exists
     */
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
