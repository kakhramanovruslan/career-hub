package com.project.authservice.service;

public interface EmailService {
    void sendAccountRegistrationEmail(String email, String username, String password);
}
