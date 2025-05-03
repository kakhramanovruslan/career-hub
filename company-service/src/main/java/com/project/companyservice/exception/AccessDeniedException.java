package com.project.companyservice.exception;

/**
 * Exception thrown when a user attempts to access a resource they are not authorized to access.
 * This exception is typically used for handling access control violations.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedException with the specified detail message.
     *
     * @param message The detail message that describes the access denial reason.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
