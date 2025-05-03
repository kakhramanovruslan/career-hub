package com.project.studentservice.controller.advice;

import com.project.studentservice.exception.AccessDeniedException;
import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.model.dto.AppExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * Global exception handler for the Student Service.
 * Handles specific exceptions and returns appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link StudentNotFoundException} and returns a bad request response with the exception message.
     *
     * @param exception the exception that was thrown
     * @return a response entity with a BAD_REQUEST status and the exception message
     */
    @ExceptionHandler(StudentNotFoundException.class)
    ResponseEntity<AppExceptionResponse> handleStudentNotFoundException(StudentNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link SQLException} and returns a bad request response with a generic error message.
     *
     * @param exception the SQL exception that was thrown
     * @return a response entity with a BAD_REQUEST status and a generic error message
     */
    @ExceptionHandler(SQLException.class)
    ResponseEntity<AppExceptionResponse> handleSqlException(SQLException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles {@link AccessDeniedException} and returns a forbidden response with the exception message.
     *
     * @param exception the exception that was thrown
     * @return a response entity with a FORBIDDEN status and the exception message
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<AppExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    /**
     * Builds a standardized exception response with the given status and message.
     *
     * @param status the HTTP status to be returned
     * @param message the error message to be included in the response
     * @return a response entity with the provided status and message
     */
    private ResponseEntity<AppExceptionResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(AppExceptionResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}
