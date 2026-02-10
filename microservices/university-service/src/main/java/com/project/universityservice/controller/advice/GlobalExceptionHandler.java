package com.project.universityservice.controller.advice;

import com.project.commons.api.ErrorResponse;
import com.project.universityservice.exception.AccessDeniedException;
import com.project.universityservice.exception.StudentNotFoundException;
import com.project.universityservice.exception.UniversityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to catch and handle specific exceptions thrown in the application.
 * This class provides custom responses for exceptions such as {@link UniversityNotFoundException},
 * {@link StudentNotFoundException}, and {@link AccessDeniedException}.
 * Each exception results in an appropriate HTTP status and response body with a message.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link UniversityNotFoundException} and returns a custom error response.
     *
     * @param exception the exception that was thrown.
     * @return a {@link ResponseEntity} with a custom error message and BAD_REQUEST status.
     */
    @ExceptionHandler(UniversityNotFoundException.class)
    ResponseEntity<ErrorResponse> handleUniversityNotFoundException(UniversityNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link StudentNotFoundException} and returns a custom error response.
     *
     * @param exception the exception that was thrown.
     * @return a {@link ResponseEntity} with a custom error message and BAD_REQUEST status.
     */
    @ExceptionHandler(StudentNotFoundException.class)
    ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link AccessDeniedException} and returns a custom error response.
     *
     * @param exception the exception that was thrown.
     * @return a {@link ResponseEntity} with a custom error message and FORBIDDEN status.
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    /**
     * Builds the {@link ResponseEntity} with a custom error message and HTTP status.
     *
     * @param status the HTTP status to be returned.
     * @param message the error message to be included in the response.
     * @return a {@link ResponseEntity} containing the error details.
     */
    private ResponseEntity<ErrorResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}
