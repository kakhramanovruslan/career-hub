package com.project.authservice.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import com.project.authservice.model.types.UserRole;

@Data
public class RegisterRequest {

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
