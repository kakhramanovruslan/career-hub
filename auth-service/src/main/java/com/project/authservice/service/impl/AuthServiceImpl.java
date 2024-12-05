package com.project.authservice.service.impl;

import com.project.authservice.client.CompanyClient;
import com.project.authservice.client.StudentClient;
import com.project.authservice.client.UniversityClient;
import com.project.authservice.exception.AccessDeniedException;
import com.project.authservice.exception.IncorrectCredentialsException;
import com.project.authservice.exception.UserAlreadyExistException;
import com.project.authservice.exception.UserNotFoundException;
import com.project.authservice.model.company.CompanyRequest;
import com.project.authservice.model.student.StudentRequest;
import com.project.authservice.model.types.UserRole;
import com.project.authservice.model.university.UniversityRequest;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final RegisterRequestMapper registerRequestMapper;
    private final UserDtoMapper userDtoMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final CompanyClient companyClient;
    private final UniversityClient universityClient;
    private final StudentClient studentClient;

    @Override
    @Transactional
    public UserDto register(RegisterRequest registerRequest) throws UserAlreadyExistException {
        if (authRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(ExceptionMessages.USER_ALREADY_EXIST);
        }

        registerRequest.setPassword(PasswordUtil.hashPassword(registerRequest.getPassword()));

        User user = authRepository.save(registerRequestMapper.toEntity(registerRequest));
        return userDtoMapper.toDto(user);
    }

    @Override
    @Transactional
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
    @Transactional
    public void delete(Long userId) {
         authRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ExceptionMessages
                .USER_NOT_FOUND));

        authRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public UserDto companyRegister(RegisterRequest request, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        UserDto userDto = register(request);
        companyClient.createCompanyProfile(
                CompanyRequest.builder()
                        .ownerId(userDto.getId())
                        .email(request.getEmail())
                        .build(),
                token
        );
        // emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());
        return userDto;
    }

    @Override
    @Transactional
    public void companyDelete(Long id, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        companyClient.deleteCompanyProfileByOwnerId(id, token);
        delete(id);
    }

    @Override
    @Transactional
    public UserDto registerUniversity(RegisterRequest request, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        UserDto userDto = register(request);
        universityClient.createUniversityProfile(UniversityRequest.builder()
                .ownerId(userDto.getId())
                .email(request.getEmail())
                .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return userDto;
    }

    @Override
    @Transactional
    public void universityDelete(Long id, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        universityClient.deleteUniversityProfileByOwnerId(id, token);
        delete(id);
    }

    @Override
    @Transactional
    public UserDto registerStudent(RegisterRequest request, Long userId, UserRole role, String token) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        UserDto userDto = register(request);
        studentClient.createStudentProfile(StudentRequest.builder()
                .ownerId(userDto.getId())
                .email(request.getEmail())
                .universityId(userId)
                .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return userDto;
    }

    @Override
    @Transactional
    public void studentDelete(Long userId, UserRole role, String token) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        delete(userId);
        studentClient.deleteStudentProfileByOwnerId(userId, token);
    }

    private boolean hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
        return true;
    }

}
