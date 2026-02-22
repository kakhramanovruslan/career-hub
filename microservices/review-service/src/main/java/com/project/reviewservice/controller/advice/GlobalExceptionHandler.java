package com.project.reviewservice.controller.advice;

import com.project.commons.api.ErrorResponse;
import com.project.reviewservice.exception.AccessDeniedException;
import com.project.reviewservice.exception.ReviewNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * Global exception handler for the review service. This class is responsible for handling various exceptions
 * throughout the application and sending appropriate HTTP responses with error details.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions related to review not being found.
     *
     * @param exception The exception thrown when a review is not found.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(ReviewNotFoundException.class)
    ResponseEntity<ErrorResponse> handleStudentNotFoundException(ReviewNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles exceptions related to access being denied.
     *
     * @param exception The exception thrown when access is denied.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    /**
     * Handles SQL exceptions that may occur during database operations.
     *
     * @param exception The exception thrown when an SQL error occurs.
     * @return A ResponseEntity containing the error message and HTTP status.
     */
    @ExceptionHandler(SQLException.class)
    ResponseEntity<ErrorResponse> handleSqlException(SQLException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Helper method to build a standardized error response.
     *
     * @param status The HTTP status to return.
     * @param message The error message to include in the response.
     * @return A ResponseEntity containing the status and error message.
     */
    private ResponseEntity<ErrorResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}
