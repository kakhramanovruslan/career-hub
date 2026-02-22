package com.project.studentservice.util;

/**
 * Utility class that holds constant exception messages used throughout the application.
 * Provides standardized messages for specific exception scenarios such as student not found or access denial.
 */
public class ExceptionMessages {

    /**
     * Message to be used when a student is not found in the system.
     */
    public static final String STUDENT_NOT_FOUND = "Student not found";

    /**
     * Message to be used when access is denied due to insufficient permissions.
     */
    public static final String ACCESS_DENIED = "Access denied: you do not have the necessary permissions to access this resource.";
}
