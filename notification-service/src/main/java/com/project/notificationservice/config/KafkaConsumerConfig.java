package com.project.notificationservice.config;

import com.project.notificationservice.dto.EmailMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for setting up Kafka consumers in the application.
 * It defines the necessary beans for consuming messages from a Kafka topic,
 * specifically for consuming messages of type {@link EmailMessageDto}.
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Creates a {@link ConsumerFactory} for consuming {@link EmailMessageDto} messages.
     *
     * @return the configured consumer factory
     */
    @Bean
    public ConsumerFactory<String, EmailMessageDto> emailConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.project.notificationservice.dto");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.project.notificationservice.dto.EmailMessageDto");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(EmailMessageDto.class));
    }

    /**
     * Creates a {@link ConcurrentKafkaListenerContainerFactory} for consuming {@link EmailMessageDto} messages.
     * This factory will be used by Kafka listeners to receive and process messages from Kafka topics.
     *
     * @return the configured Kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailMessageDto> emailKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailConsumerFactory());
        return factory;
    }
}
