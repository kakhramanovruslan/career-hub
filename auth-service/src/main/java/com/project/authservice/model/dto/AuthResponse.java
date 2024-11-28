package com.project.authservice.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AuthResponse {

    private String token;
}
