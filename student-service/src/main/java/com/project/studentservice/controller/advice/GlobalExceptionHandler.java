package com.project.studentservice.controller.advice;

import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.model.dto.AppExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    ResponseEntity<AppExceptionResponse> handleStudentNotFoundException(StudentNotFoundException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    // todo Make SQLException handler safer, we can't give messages from SQL to client
    @ExceptionHandler(SQLException.class)
    ResponseEntity<AppExceptionResponse> handleSqlException(SQLException exception) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<AppExceptionResponse> buildExceptionResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(AppExceptionResponse.builder()
                .status(status.value())
                .message(message)
                .build()
        );
    }
}
