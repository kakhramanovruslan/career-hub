package com.project.authservice.service;

/**
 * Service interface for sending emails related to account registration.
 * Provides methods to send welcome emails with user credentials.
 */
public interface EmailService {

    /**
     * Sends an account registration email to the specified email address.
     * The email contains the account credentials (username and password).
     *
     * @param email the email address of the user to send the email to
     * @param username the username for the newly created account
     * @param password the password for the newly created account
     */
    void sendAccountRegistrationEmail(String email, String username, String password);
}
