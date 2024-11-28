package com.project.authservice.service.impl;

import com.project.authservice.exception.IncorrectCredentialsException;
import com.project.authservice.exception.UserAlreadyExistException;
import com.project.authservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.project.authservice.mapper.RegisterRequestMapper;
import com.project.authservice.mapper.UserDtoMapper;
import com.project.authservice.model.dto.AuthRequest;
import com.project.authservice.model.dto.AuthResponse;
import com.project.authservice.model.dto.RegisterRequest;
import com.project.authservice.model.dto.UserDto;
import com.project.authservice.model.entity.User;
import org.springframework.stereotype.Service;
import com.project.authservice.repository.AuthRepository;
import com.project.authservice.service.AuthService;
import com.project.authservice.util.ExceptionMessages;
import com.project.authservice.util.JwtTokenUtil;
import com.project.authservice.util.PasswordUtil;

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
        }

        registerRequest.setPassword(PasswordUtil.hashPassword(registerRequest.getPassword()));

        User user = authRepository.save(registerRequestMapper.toEntity(registerRequest));
        return userDtoMapper.toDto(user);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) throws IncorrectCredentialsException {
        User user = authRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new IncorrectCredentialsException(ExceptionMessages.INCORRECT_CREDENTIALS));

        if (!PasswordUtil.checkPassword(authRequest.getPassword(), user.getPassword())){
            throw new IncorrectCredentialsException(ExceptionMessages.INCORRECT_CREDENTIALS);
        }

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtTokenUtil.generateToken(user.getId(), user.getRole()));

        return authResponse;
    }

    @Override
    public void delete(Long userId) {
         authRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ExceptionMessages
                .USER_NOT_FOUND));

        authRepository.deleteById(userId);
    }


}
