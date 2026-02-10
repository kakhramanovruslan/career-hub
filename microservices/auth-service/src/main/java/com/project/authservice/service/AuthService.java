package com.project.authservice.service;

import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;
import com.project.authservice.model.types.UserRole;

/**
 * Service interface for managing user authentication and registration processes.
 * This includes user login, registration, and deletion for various user roles such as company, university, and student.
 */
public interface AuthService {

    /**
     * Registers a new user.
     *
     * @param registerRequest the details of the user to register
     * @return the created UserDto
     */
    UserDto register(RegisterRequest registerRequest);

    /**
     * Authenticates the user and generates an authentication token.
     *
     * @param authRequest the login credentials of the user
     * @return the AuthResponse containing the authentication token
     */
    AuthResponse login(AuthRequest authRequest);

    /**
     * Deletes a user by their ID.
     *
     * @param userId the ID of the user to delete
     */
    void delete(Long userId);

    /**
     * Registers a company by creating a new user and company profile.
     *
     * @param request the details of the company to register
     * @param role the role of the user performing the registration (should be ADMIN)
     * @param token the token of the requesting user for authentication
     * @return the created UserDto
     */
    UserDto companyRegister(RegisterRequest request, UserRole role, String token);

    /**
     * Deletes a company by its owner ID.
     *
     * @param id the owner ID of the company to delete
     * @param role the role of the user performing the deletion (should be ADMIN)
     * @param token the token of the requesting user for authentication
     */
    void companyDelete(Long id, UserRole role, String token);

    /**
     * Registers a university by creating a new user and university profile.
     *
     * @param request the details of the university to register
     * @param role the role of the user performing the registration (should be ADMIN)
     * @param token the token of the requesting user for authentication
     * @return the created UserDto
     */
    UserDto registerUniversity(RegisterRequest request, UserRole role, String token);

    /**
     * Deletes a university by its owner ID.
     *
     * @param id the owner ID of the university to delete
     * @param role the role of the user performing the deletion (should be ADMIN)
     * @param token the token of the requesting user for authentication
     */
    void universityDelete(Long id, UserRole role, String token);

    /**
     * Registers a student by creating a new user and student profile.
     *
     * @param request the details of the student to register
     * @param userId the ID of the university to associate the student with
     * @param role the role of the user performing the registration (should be UNIVERSITY)
     * @param token the token of the requesting user for authentication
     * @return the created UserDto
     */
    UserDto registerStudent(RegisterRequest request, Long userId, UserRole role, String token);

    /**
     * Deletes a student by their ID.
     *
     * @param userId the ID of the student to delete
     * @param role the role of the user performing the deletion (should be UNIVERSITY)
     * @param token the token of the requesting user for authentication
     */
    void studentDelete(Long userId, UserRole role, String token);
}
