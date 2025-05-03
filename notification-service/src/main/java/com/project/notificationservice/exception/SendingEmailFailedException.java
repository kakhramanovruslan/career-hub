package com.project.notificationservice.exception;

/**
 * Exception thrown when email sending fails.
 * This exception can be used to indicate errors that occur during the process of sending an email.
 */
public class SendingEmailFailedException extends RuntimeException {

    /**
     * Constructs a new {@code SendingEmailFailedException} with the specified detail message.
     *
     * @param message the detail message
     */
    public SendingEmailFailedException(String message) {
        super(message);
    }
}
