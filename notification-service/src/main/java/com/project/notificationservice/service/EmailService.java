package com.project.notificationservice.service;

import com.project.notificationservice.dto.EmailMessageDto;

public interface EmailService {

    void send(EmailMessageDto message);
}