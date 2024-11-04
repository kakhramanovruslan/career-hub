package controller;

import model.dto.AuthRequest;
import model.dto.AuthResponse;
import model.dto.RegisterRequest;
import model.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/registration")
    public ResponseEntity<UserDto> register(RegisterRequest user) {
        return ResponseEntity.ok(new UserDto());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(AuthRequest user) {
        return ResponseEntity.ok(new AuthResponse());
    }
}
