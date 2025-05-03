package com.project.studentservice.exception;

/**
 * Exception thrown when access is denied due to insufficient permissions.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the access denial.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
