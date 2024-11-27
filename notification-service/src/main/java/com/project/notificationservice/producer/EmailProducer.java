//package com.project.notificationservice.producer;
//
//import com.project.notificationservice.dto.EmailMessageDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class EmailProducer {
//
//    private final KafkaTemplate<String, EmailMessageDto> emailKafkaTemplate;
//
//
//    public void send(String topic, EmailMessageDto emailMessage) {
//        emailKafkaTemplate.send(topic, emailMessage);
//    }
//}
