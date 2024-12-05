package com.project.authservice.controller;

import com.project.authservice.client.CompanyClient;
import com.project.authservice.client.StudentClient;
import com.project.authservice.client.UniversityClient;
import com.project.authservice.exception.AccessDeniedException;
import com.project.authservice.model.company.CompanyRequest;
import com.project.authservice.model.dto.*;
import com.project.authservice.model.student.StudentRequest;
import com.project.authservice.model.types.UserRole;
import com.project.authservice.model.university.UniversityRequest;
import com.project.authservice.service.EmailService;
import com.project.authservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.authservice.service.AuthService;

import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final CompanyClient companyClient;
    private final StudentClient studentClient;
    private final UniversityClient universityClient;

//    @PostMapping("/registration")
//    public ResponseEntity<UserDto> register(@RequestBody RegisterRequest user, @RequestHeader("X-User-Role") UserRole role) {
//        hasRole(role, List.of(UserRole.ADMIN));
//        UserDto userDto = authService.register(user);
//        emailService.sendAccountRegistrationEmail(user.getEmail(), user.getUsername(), user.getPassword());
//        return ResponseEntity.ok().body(userDto);
//    }

    @PostMapping("/company/registration")
    public ResponseEntity<UserDto> companyRegister(@RequestBody RegisterRequest request,
                                                        @RequestHeader("X-User-Role") UserRole role,
                                                        @RequestHeader("Authorization") String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        UserDto userDto = authService.register(request);
        companyClient.createCompanyProfile(CompanyRequest.builder()
                        .ownerId(userDto.getId())
                        .email(request.getEmail())
                        .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(userDto);

    }

    @DeleteMapping("/company/delete/{id}")
    public ResponseEntity<HttpStatus> companyDelete(@PathVariable Long id,
                                                    @RequestHeader("X-User-Role") UserRole role,
                                                    @RequestHeader("Authorization") String token) {
        hasRole(role, List.of(UserRole.ADMIN));

        companyClient.deleteCompanyProfileByOwnerId(id, token);
        authService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);

    }



    @PostMapping("/university/registration")
    public ResponseEntity<UserDto> universityRegister(@RequestBody RegisterRequest request,
                                                           @RequestHeader("X-User-Role") UserRole role,
                                                           @RequestHeader("Authorization") String token){
        hasRole(role, List.of(UserRole.ADMIN));

        UserDto userDto = authService.register(request);
        universityClient.createUniversityProfile(UniversityRequest.builder()
                        .ownerId(userDto.getId())
                        .email(request.getEmail())
                        .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(userDto);

    }

    @DeleteMapping("/university/delete/{id}")
    public ResponseEntity<HttpStatus> universityDelete(@PathVariable Long id,
                                                       @RequestHeader("X-User-Role") UserRole role,
                                                       @RequestHeader("Authorization") String token) {

        hasRole(role, List.of(UserRole.ADMIN));

        universityClient.deleteUniversityProfileByOwnerId(id, token);
        authService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping("/student/registration")
    public ResponseEntity<UserDto> studentRegister(@RequestBody RegisterRequest request,
                                                        @RequestHeader("X-User-Role") UserRole role,
                                                        @RequestHeader("X-User-Id") Long userId,
                                                        @RequestHeader("Authorization") String token) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        UserDto userDto = authService.register(request);
        studentClient.createStudentProfile(StudentRequest.builder()
                                                         .ownerId(userDto.getId())
                                                         .email(request.getEmail())
                                                         .universityId(userId)
                                                         .build(), token);
//        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(userDto);

    }

    @DeleteMapping("/student/delete/{userId}")
    public ResponseEntity<HttpStatus> studentDelete(@PathVariable Long userId,
                                                    @RequestHeader("X-User-Role") UserRole role,
                                                    @RequestHeader("Authorization") String token) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        authService.delete(userId);
        studentClient.deleteStudentProfileByOwnerId(userId, token);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest user) {
        return ResponseEntity.ok().body(authService.login(user));
    }

    private boolean hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
        return true;
    }
}
/*
1) EmailService перенести логику от register. DONE

2) if else-ы на то чтобы только университеты могли регистрировать студентов

2) Feign Client - для удаления, и создать с пустыми значениями профили. 1

Optional 3) Вход через username or email.
*/
