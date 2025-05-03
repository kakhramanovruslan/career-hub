package com.project.authservice.controller;

import com.project.authservice.model.dto.*;
import com.project.authservice.model.types.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.authservice.service.AuthService;

/**
 * Controller for handling authentication and user registration processes.
 * This includes registration and deletion of companies, universities, and students.
 */
@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a company using the provided registration request.
     *
     * @param request the registration details for the company
     * @param role the role of the user
     * @param token the JWT token for authorization
     * @return the registered company user details
     */
    @PostMapping("/company/registration")
    public ResponseEntity<UserDto> companyRegister(@RequestBody RegisterRequest request,
                                                   @RequestHeader("X-User-Role") UserRole role,
                                                   @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.companyRegister(request, role, token));
    }

    /**
     * Deletes a company by its ID.
     *
     * @param id the ID of the company to delete
     * @param role the role of the user performing the action
     * @param token the JWT token for authorization
     * @return the HTTP status of the operation
     */
    @DeleteMapping("/company/delete/{id}")
    public ResponseEntity<HttpStatus> companyDelete(@PathVariable Long id,
                                                    @RequestHeader("X-User-Role") UserRole role,
                                                    @RequestHeader("Authorization") String token) {
        authService.companyDelete(id, role, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Registers a university using the provided registration request.
     *
     * @param request the registration details for the university
     * @param role the role of the user
     * @param token the JWT token for authorization
     * @return the registered university user details
     */
    @PostMapping("/university/registration")
    public ResponseEntity<UserDto> universityRegister(@RequestBody RegisterRequest request,
                                                      @RequestHeader("X-User-Role") UserRole role,
                                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authService.registerUniversity(request, role, token));
    }

    /**
     * Deletes a university by its ID.
     *
     * @param id the ID of the university to delete
     * @param role the role of the user performing the action
     * @param token the JWT token for authorization
     * @return the HTTP status of the operation
     */
    @DeleteMapping("/university/delete/{id}")
    public ResponseEntity<HttpStatus> universityDelete(@PathVariable Long id,
                                                       @RequestHeader("X-User-Role") UserRole role,
                                                       @RequestHeader("Authorization") String token) {
        authService.universityDelete(id, role, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Registers a student using the provided registration request.
     *
     * @param request the registration details for the student
     * @param role the role of the user
     * @param userId the ID of the user performing the registration
     * @param token the JWT token for authorization
     * @return the registered student user details
     */
    @PostMapping("/student/registration")
    public ResponseEntity<UserDto> studentRegister(@RequestBody RegisterRequest request,
                                                   @RequestHeader("X-User-Role") UserRole role,
                                                   @RequestHeader("X-User-Id") Long userId,
                                                   @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.registerStudent(request, userId, role, token));
    }

    /**
     * Deletes a student by their ID.
     *
     * @param userId the ID of the student to delete
     * @param role the role of the user performing the action
     * @param token the JWT token for authorization
     * @return the HTTP status of the operation
     */
    @DeleteMapping("/student/delete/{userId}")
    public ResponseEntity<HttpStatus> studentDelete(@PathVariable Long userId,
                                                    @RequestHeader("X-User-Role") UserRole role,
                                                    @RequestHeader("Authorization") String token) {
        authService.studentDelete(userId, role, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Authenticates the user and returns an authentication response with a token.
     *
     * @param user the login credentials for the user
     * @return the authentication response containing the JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest user) {
        return ResponseEntity.ok().body(authService.login(user));
    }
}
