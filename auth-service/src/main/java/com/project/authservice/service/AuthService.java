package com.project.authservice.service;

import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;

public interface AuthService {

    UserDto register(RegisterRequest registerRequest);

    AuthResponse login(AuthRequest authRequest);

    void delete(Long userId);
}
