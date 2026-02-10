package com.project.notificationservice.service.impl;

import com.project.commons.contract.messaging.EmailMessageDto;
import com.project.notificationservice.exception.SendingEmailFailedException;
import com.project.notificationservice.util.ExceptionMessages;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.project.notificationservice.service.EmailService;

import java.nio.charset.StandardCharsets;

/**
 * Implementation of the {@link EmailService} interface for sending email notifications.
 * This service uses JavaMailSender to send emails to users.
 */
@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.sender.email}")
    private String sender;

    /**
     * Sends an email message using JavaMailSender.
     *
     * @param message The email message to be sent.
     * @throws SendingEmailFailedException if the email could not be sent due to an error.
     */
    @Override
    public void send(EmailMessageDto message) throws SendingEmailFailedException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            var helper = new MimeMessageHelper(
                    mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name()
            );

            helper.setFrom(sender);
            helper.setTo(message.getEmail());
            helper.setSubject(message.getSubject());
            helper.setText(message.getBody());

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new SendingEmailFailedException(ExceptionMessages.SENDING_EMAIL_FAILED);
        }
    }
}
