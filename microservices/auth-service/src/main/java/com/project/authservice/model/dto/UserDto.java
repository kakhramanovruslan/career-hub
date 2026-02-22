package com.project.authservice.model.dto;

import jakarta.persistence.*;
import lombok.Data;
import com.project.authservice.model.types.UserRole;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
