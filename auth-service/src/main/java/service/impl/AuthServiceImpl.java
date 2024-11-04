package service.impl;

import exception.IncorrectCredentialsException;
import exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.RegisterRequestMapper;
import mapper.UserDtoMapper;
import model.dto.AuthRequest;
import model.dto.AuthResponse;
import model.dto.RegisterRequest;
import model.dto.UserDto;
import model.entity.User;
import org.springframework.stereotype.Service;
import repository.AuthRepository;
import service.AuthService;
import util.ExceptionMessages;
import util.JwtTokenUtil;
import util.PasswordUtil;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final RegisterRequestMapper registerRequestMapper;
    private final UserDtoMapper userDtoMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDto register(RegisterRequest registerRequest) throws UserAlreadyExistException {
        if (authRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(ExceptionMessages.USER_ALREADY_EXIST);
        };

        registerRequest.setPassword(PasswordUtil.hashPassword(registerRequest.getPassword()));

        User user = authRepository.save(registerRequestMapper.toEntity(registerRequest));
        return userDtoMapper.toDto(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        User user = authRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new IncorrectCredentialsException(ExceptionMessages.INCORRECT_CREDENTIALS));

        if (!PasswordUtil.checkPassword(authRequest.getPassword(), user.getPassword())){
            throw new IncorrectCredentialsException(ExceptionMessages.INCORRECT_CREDENTIALS);
        };

        return jwtTokenUtil.generateToken();
    }
}
