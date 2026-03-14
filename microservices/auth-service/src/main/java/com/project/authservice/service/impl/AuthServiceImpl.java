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
import com.project.authservice.service.EmailService;
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

/**
 * Implementation of the {@link AuthService} interface providing methods for user authentication and registration.
 * It includes operations such as registering a user, logging in, deleting users, and managing company, university,
 * and student profiles.
 */
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
    private final EmailService emailService;

    /**
     * Registers a new user in the system.
     *
     * @param registerRequest The registration data for the user.
     * @return The registered user details as a {@link UserDto}.
     * @throws UserAlreadyExistException If the user with the same username already exists.
     */
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

    /**
     * Logs in a user with the provided credentials.
     *
     * @param authRequest The login credentials for the user.
     * @return The authentication response containing the generated JWT token.
     * @throws IncorrectCredentialsException If the credentials are incorrect.
     */
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

    /**
     * Deletes a user from the system by their ID.
     *
     * @param userId The ID of the user to delete.
     * @throws UserNotFoundException If no user exists with the given ID.
     */
    @Override
    @Transactional
    public void delete(Long userId) {
        authRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(ExceptionMessages
                .USER_NOT_FOUND));

        authRepository.deleteById(userId);
    }

    /**
     * Registers a company in the system.
     *
     * @param request The registration data for the company.
     * @param role The role of the user initiating the request.
     * @param token The authentication token of the requester.
     * @return The registered company details as a {@link UserDto}.
     */
    @Override
    @Transactional
    public UserDto companyRegister(RegisterRequest request, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));
        String password = request.getPassword(); //TODO Change to temporary password

        UserDto userDto = register(request);
        companyClient.createCompanyProfile(
                CompanyRequest.builder()
                        .ownerId(userDto.getId())
                        .email(request.getEmail())
                        .build(),
                token
        );
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), password);
        return userDto;
    }

    /**
     * Deletes a company from the system by its owner's ID.
     *
     * @param id The ID of the company owner.
     * @param role The role of the user initiating the request.
     * @param token The authentication token of the requester.
     */
    @Override
    @Transactional
    public void companyDelete(Long id, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        companyClient.deleteCompanyProfileByOwnerId(id, token);
        delete(id);
    }

    /**
     * Registers a university in the system.
     *
     * @param request The registration data for the university.
     * @param role The role of the user initiating the request.
     * @param token The authentication token of the requester.
     * @return The registered university details as a {@link UserDto}.
     */
    @Override
    @Transactional
    public UserDto registerUniversity(RegisterRequest request, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));
        String password = request.getPassword(); //TODO Change to temporary password
        UserDto userDto = register(request);
        universityClient.createUniversityProfile(UniversityRequest.builder()
                .ownerId(userDto.getId())
                .email(request.getEmail())
                .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), password);

        return userDto;
    }

    /**
     * Deletes a university from the system by its owner's ID.
     *
     * @param id The ID of the university owner.
     * @param role The role of the user initiating the request.
     * @param token The authentication token of the requester.
     */
    @Override
    @Transactional
    public void universityDelete(Long id, UserRole role, String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        universityClient.deleteUniversityProfileByOwnerId(id, token);
        delete(id);
    }

    /**
     * Registers a student in the system.
     *
     * @param request The registration data for the student.
     * @param userId The ID of the university associated with the student.
     * @param role The role of the user initiating the request.
     * @param token The authentication token of the requester.
     * @return The registered student details as a {@link UserDto}.
     */
    @Override
    @Transactional
    public UserDto registerStudent(RegisterRequest request, Long userId, UserRole role, String token) {
        hasRole(role, List.of(UserRole.UNIVERSITY));
        String password = request.getPassword(); //TODO Change to temporary password

        UserDto userDto = register(request);
        studentClient.createStudentProfile(StudentRequest.builder()
                .ownerId(userDto.getId())
                .email(request.getEmail())
                .universityId(userId)
                .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), password);

        return userDto;
    }

    /**
     * Deletes a student from the system by their ID.
     *
     * @param userId The ID of the student to delete.
     * @param role The role of the user initiating the request.
     * @param token The authentication token of the requester.
     */
    @Override
    @Transactional
    public void studentDelete(Long userId, UserRole role, String token) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        delete(userId);
        studentClient.deleteStudentProfileByOwnerId(userId, token);
    }

    /**
     * Checks if the current user has one of the required roles.
     *
     * @param currentRole The role of the current user.
     * @param requiredRoles The list of roles that are allowed to perform the action.
     * @throws AccessDeniedException If the current role is not one of the required roles.
     */
    private boolean hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
        return true;
    }
}
