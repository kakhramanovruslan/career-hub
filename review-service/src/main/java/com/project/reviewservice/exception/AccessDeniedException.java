package com.project.reviewservice.exception;

/**
 * Exception thrown when a user attempts to perform an action they are not authorized to do.
 * This exception is typically used when access to a specific resource is denied due to insufficient permissions.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedException with the specified detail message.
     *
     * @param message The detail message explaining the reason for the access denial.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
