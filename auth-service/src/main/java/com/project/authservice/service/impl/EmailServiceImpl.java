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
        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .email(email)
                .subject("Account registered")
                .body(username + " " + password)
                .build();
        emailProducer.send("email", emailMessageDto);
    }
}
