package com.project.authservice.service;

import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;

public interface AuthService {

    public UserDto register(RegisterRequest registerRequest);

    public AuthResponse login(AuthRequest authRequest);
}
