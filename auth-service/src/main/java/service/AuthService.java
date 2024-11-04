package service;

import model.dto.AuthRequest;
import model.dto.AuthResponse;
import model.dto.RegisterRequest;
import model.dto.UserDto;

public interface AuthService {

    public UserDto register(RegisterRequest registerRequest);

    public AuthResponse login(AuthRequest authRequest);
}
