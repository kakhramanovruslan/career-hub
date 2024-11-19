package com.project.notificationservice.consumer;

import com.project.notificationservice.dto.EmailMessageDto;
import com.project.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "email", groupId = "notification-service",
            containerFactory = "emailKafkaListenerContainerFactory")
    void listener(EmailMessageDto emailMessage) {
        try{
            emailService.send(emailMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
