package com.project.notificationservice.service;

import com.project.commons.contract.messaging.EmailMessageDto;

/**
 * Service interface for sending email notifications.
 * This interface provides a method to send an email message.
 */
public interface EmailService {

    /**
     * Sends an email message.
     *
     * @param message The email message to be sent.
     */
    void send(EmailMessageDto message);
}
