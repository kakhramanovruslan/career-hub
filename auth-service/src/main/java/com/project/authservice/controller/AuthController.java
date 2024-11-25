package com.project.authservice.controller;

import com.project.authservice.exception.AccessDeniedException;
import com.project.authservice.model.types.UserRole;
import com.project.authservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.authservice.service.AuthService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest user, @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.ADMIN));
        return ResponseEntity.ok().body(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest user) {
        return ResponseEntity.ok().body(authService.login(user));
    }

    private boolean hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
        return true;
    }
}
