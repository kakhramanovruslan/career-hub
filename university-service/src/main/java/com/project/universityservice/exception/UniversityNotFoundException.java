package com.project.universityservice.exception;

public class UniversityNotFoundException extends RuntimeException{
    public UniversityNotFoundException(String message) {
        super(message);
    }
}
