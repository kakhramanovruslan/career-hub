package model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import model.types.UserRole;

@Data
public class RegisterRequest {

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
