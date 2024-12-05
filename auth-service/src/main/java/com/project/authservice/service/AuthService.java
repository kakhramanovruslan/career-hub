package com.project.authservice.service;

import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;
import com.project.authservice.model.types.UserRole;

public interface AuthService {

    UserDto register(RegisterRequest registerRequest);

    AuthResponse login(AuthRequest authRequest);

    void delete(Long userId);

    UserDto companyRegister(RegisterRequest request, UserRole role, String token);

    void companyDelete(Long id, UserRole role, String token);

    UserDto registerUniversity(RegisterRequest request, UserRole role, String token);

    void universityDelete(Long id, UserRole role, String token);

    UserDto registerStudent(RegisterRequest request, Long userId, UserRole role, String token);

    void studentDelete(Long userId, UserRole role, String token);
}
