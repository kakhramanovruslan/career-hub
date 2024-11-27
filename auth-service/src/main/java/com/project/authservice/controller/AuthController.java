package com.project.authservice.controller;

import com.project.authservice.exception.AccessDeniedException;
import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;
import com.project.authservice.model.types.UserRole;
import com.project.authservice.producer.EmailProducer;
import com.project.authservice.util.ExceptionMessages;
import com.project.notificationservice.dto.EmailMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.authservice.service.AuthService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final EmailProducer emailProducer;

    @PostMapping("/registration")
    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest user, @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.ADMIN));
        UserDto userDto = authService.register(user);
        EmailMessageDto emailMessageDto = EmailMessageDto.builder()
                .email(user.getEmail()).subject("Account registered").body(user.getUsername() + " " +user.getPassword()).build();
        emailProducer.send("email", emailMessageDto);
        return ResponseEntity.ok().body(userDto);
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

/*
1) EmailService перенести логику от register.

2) if else-ы на то чтобы только университеты могли регистрировать студентов

2) Feign Client - для удаления, и создать с пустыми значениями профили.

Optional 3) Вход через username or email.
*/
