package com.project.companyservice.controller.advice;

import com.project.companyservice.exception.AccessDeniedException;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.model.dto.AppExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler to manage custom exceptions for the company service.
 * This class handles exceptions globally within the company service and provides appropriate HTTP responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles CompanyNotFoundException and returns an appropriate response.
     *
     * @param exception The exception that was thrown.
     * @return A ResponseEntity containing the exception response with status 400 (BAD_REQUEST).
     */
    @ExceptionHandler(CompanyNotFoundException.class)
    ResponseEntity<AppExceptionResponse> handleStudentNotFoundException(CompanyNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    /**
     * Handles AccessDeniedException and returns an appropriate response.
     *
     * @param exception The exception that was thrown.
     * @return A ResponseEntity containing the exception response with status 403 (FORBIDDEN).
     */
    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<AppExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    /**
     * Builds a standardized exception response.
     *
     * @param status The HTTP status to be returned.
     * @param message The exception message to be included in the response body.
     * @return A ResponseEntity containing the exception response with the provided status and message.
     */
    private ResponseEntity<AppExceptionResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(AppExceptionResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}
