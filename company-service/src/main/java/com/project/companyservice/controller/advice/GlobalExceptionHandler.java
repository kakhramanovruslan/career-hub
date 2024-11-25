package com.project.companyservice.controller.advice;

import com.project.companyservice.exception.AccessDeniedException;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.model.dto.AppExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyNotFoundException.class)
    ResponseEntity<AppExceptionResponse> handleStudentNotFoundException(CompanyNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<AppExceptionResponse> handleAccessDeniedException(AccessDeniedException exception) {
        return buildExceptionResponse(HttpStatus.FORBIDDEN, exception.getMessage());
    }

    private ResponseEntity<AppExceptionResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(AppExceptionResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}