package com.project.authservice.producer;

import com.project.notificationservice.dto.EmailMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service responsible for producing email messages and sending them to Kafka topics.
 * The messages are forwarded to a Kafka topic for further processing, such as
 * email delivery notifications or queues for asynchronous email sending.
 */
@Service
@RequiredArgsConstructor
public class EmailProducer {

    /**
     * KafkaTemplate for sending email messages to Kafka topics.
     */
    private final KafkaTemplate<String, EmailMessageDto> emailKafkaTemplate;

    /**
     * Sends an email message to a specified Kafka topic.
     *
     * @param topic The Kafka topic to which the email message will be sent.
     * @param emailMessage The email message to be sent, containing the necessary data.
     */
    public void send(String topic, EmailMessageDto emailMessage) {
        emailKafkaTemplate.send(topic, emailMessage);
    }
}
