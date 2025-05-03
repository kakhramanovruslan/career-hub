package com.project.authservice.controller.advice;

import com.project.authservice.exception.AccessDeniedException;
import com.project.authservice.exception.IncorrectCredentialsException;
import com.project.authservice.exception.UserAlreadyExistException;
import com.project.authservice.exception.UserNotFoundException;
import com.project.authservice.model.dto.AppExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * Global exception handler for the authentication service.
 * This class handles various exceptions that might occur in the application,
 * such as user-related errors, SQL exceptions, and access control issues.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles the UserAlreadyExistException.
     *
     * @param exception the exception details
     * @return a response entity with the error message and BAD_REQUEST status
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    ResponseEntity<AppExceptionResponse> handleUserAlreadyExistException (UserAlreadyExistException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles the IncorrectCredentialsException.
     *
     * @param exception the exception details
     * @return a response entity with the error message and BAD_REQUEST status
     */
    @ExceptionHandler(IncorrectCredentialsException.class)
    ResponseEntity<AppExceptionResponse> handleIncorrectCredentialsException(IncorrectCredentialsException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles the AccessDeniedException.
     *
     * @param exception the exception details
     * @return a response entity with the error message and FORBIDDEN status
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<AppExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    /**
     * Handles the UserNotFoundException.
     *
     * @param exception the exception details
     * @return a response entity with the error message and BAD_REQUEST status
     */
    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<AppExceptionResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles the SQLException. This handler is more cautious and doesn't expose SQL messages directly to the client.
     *
     * @param exception the exception details
     * @return a response entity with a generic error message and BAD_REQUEST status
     */
    @ExceptionHandler(SQLException.class)
    ResponseEntity<AppExceptionResponse> handleSqlException(SQLException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, "An error occurred while processing the request.");
    }

    /**
     * Builds the exception response with the provided status and message.
     *
     * @param status the HTTP status
     * @param message the exception message
     * @return a response entity containing the exception details
     */
    private ResponseEntity<AppExceptionResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(AppExceptionResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}
