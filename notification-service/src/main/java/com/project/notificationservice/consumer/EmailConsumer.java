package com.project.notificationservice.consumer;

import com.project.notificationservice.dto.EmailMessageDto;
import com.project.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer for consuming email messages from the Kafka topic.
 * This class listens to the "email" topic and processes incoming {@link EmailMessageDto} messages.
 * It delegates the email sending task to the {@link EmailService}.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;

    /**
     * Kafka listener method for consuming {@link EmailMessageDto} messages from the "email" topic.
     * It triggers the {@link EmailService#send(EmailMessageDto)} method to send the email.
     *
     * @param emailMessage the email message received from the Kafka topic
     */
    @KafkaListener(topics = "email", groupId = "notification-service",
            containerFactory = "emailKafkaListenerContainerFactory")
    void listener(EmailMessageDto emailMessage) {
        try {
            emailService.send(emailMessage);
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
        }
    }
}
