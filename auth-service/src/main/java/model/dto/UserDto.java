package model.dto;

import jakarta.persistence.*;
import lombok.Data;
import model.types.UserRole;

@Data
public class UserDto {

    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
