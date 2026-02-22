package com.project.reviewservice.exception;

/**
 * Exception thrown when a review cannot be found in the system.
 * This exception is typically used when trying to retrieve a review by its ID or when a review is expected but doesn't exist.
 */
public class ReviewNotFoundException extends RuntimeException {

    /**
     * Constructs a new ReviewNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining the reason the review was not found.
     */
    public ReviewNotFoundException(String message) {
        super(message);
    }
}
