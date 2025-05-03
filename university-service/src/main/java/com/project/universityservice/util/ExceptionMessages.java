package com.project.universityservice.util;

/**
 * A utility class that holds common exception messages used throughout the application.
 * These messages are intended to provide consistent and reusable exception messages
 * for various error scenarios such as university or student not found, or access denial.
 */
public class ExceptionMessages {

    /**
     * The message used when a university cannot be found.
     */
    public static final String UNIVERSITY_NOT_FOUND = "University not found";

    /**
     * The message used when a student cannot be found.
     */
    public static final String STUDENT_NOT_FOUND = "Student not found";

    /**
     * The message used when access to a resource is denied due to insufficient permissions.
     */
    public static final String ACCESS_DENIED = "Access denied: you do not have the necessary permissions to access this resource.";

}
