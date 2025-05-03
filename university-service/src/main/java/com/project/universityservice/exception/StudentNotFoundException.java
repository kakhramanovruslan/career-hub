package com.project.universityservice.exception;

/**
 * Exception thrown when a student cannot be found in the system.
 * Extends {@link RuntimeException} to allow for unchecked exceptions.
 */
public class StudentNotFoundException extends RuntimeException {

    /**
     * Constructs a new StudentNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining why the student was not found.
     */
    public StudentNotFoundException(String message) {
        super(message);
    }
}
