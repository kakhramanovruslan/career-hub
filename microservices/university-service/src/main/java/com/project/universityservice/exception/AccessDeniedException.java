package com.project.universityservice.exception;

/**
 * Exception thrown when access to a resource is denied due to insufficient permissions.
 * Extends {@link RuntimeException} to allow for unchecked exceptions.
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedException with the specified detail message.
     *
     * @param message the detail message explaining why the access is denied.
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
