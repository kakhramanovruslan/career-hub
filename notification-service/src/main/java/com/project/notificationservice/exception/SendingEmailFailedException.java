package com.project.notificationservice.exception;

public class SendingEmailFailedException extends RuntimeException {
    public SendingEmailFailedException(String message) {
        super(message);
    }
}
