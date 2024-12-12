package com.project.authservice.service.impl;

import com.project.authservice.producer.EmailProducer;
import com.project.authservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.project.notificationservice.dto.EmailMessageDto;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final EmailProducer emailProducer;

    @Override
    public void sendAccountRegistrationEmail(String email, String username, String password) {


        String body = String.format("""
        Hello, %s!
        
        Your account has been successfully created on our platform. We are excited to welcome you aboard!

        Here are your login credentials:

        - Username: %s
        - Password: %s

        Please make sure to store this information in a safe place. For your security, we recommend changing your password after your first login.

        If you have any questions or need assistance, feel free to contact our support team at support@careerhub.com.

        Best regards,
        The Career-Hub Team
        """, username, username, password);


        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .email(email)
                .subject("Account successfully registered!")
                .body(body)
                .build();
        emailProducer.send("email", emailMessageDto);
    }
}
