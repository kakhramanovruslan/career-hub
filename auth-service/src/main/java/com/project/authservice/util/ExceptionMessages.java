package com.project.authservice.util;

/**
 * Utility class that contains constant strings for error messages used throughout the application.
 * These messages are used to provide consistent and readable error responses for various exceptions.
 */
public class ExceptionMessages {

    /**
     * Error message indicating that the user already exists.
     */
    public static final String USER_ALREADY_EXIST = "User already exist";

    /**
     * Error message indicating that the provided username or password is incorrect.
     */
    public static final String INCORRECT_CREDENTIALS = "Incorrect username or password";

    /**
     * Error message indicating that access to a resource is denied due to insufficient permissions.
     */
    public static final String ACCESS_DENIED = "Access denied: you do not have the necessary permissions to access this resource.";

    /**
     * Error message indicating that the user was not found.
     */
    public static final String USER_NOT_FOUND = "User not found.";
}
