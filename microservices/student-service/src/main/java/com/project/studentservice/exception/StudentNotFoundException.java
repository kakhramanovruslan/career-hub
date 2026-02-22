package com.project.studentservice.exception;

/**
 * Exception thrown when a student is not found in the system.
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
