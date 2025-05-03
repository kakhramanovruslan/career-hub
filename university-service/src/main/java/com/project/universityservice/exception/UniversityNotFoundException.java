package com.project.universityservice.exception;

/**
 * Exception thrown when a university cannot be found in the system.
 * Extends {@link RuntimeException} to allow for unchecked exceptions.
 */
public class UniversityNotFoundException extends RuntimeException {

    /**
     * Constructs a new UniversityNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining why the university was not found.
     */
    public UniversityNotFoundException(String message) {
        super(message);
    }
}
