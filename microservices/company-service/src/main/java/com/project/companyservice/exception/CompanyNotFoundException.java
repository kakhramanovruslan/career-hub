package com.project.companyservice.exception;

/**
 * Exception thrown when a company is not found in the system.
 * This is typically used when an operation attempts to retrieve or manipulate a company that does not exist.
 */
public class CompanyNotFoundException extends RuntimeException {

    /**
     * Constructs a new CompanyNotFoundException with the specified detail message.
     *
     * @param message The detail message explaining why the company was not found.
     */
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
