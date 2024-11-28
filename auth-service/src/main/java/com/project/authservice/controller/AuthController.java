package com.project.authservice.controller;

import com.project.authservice.client.CompanyClient;
import com.project.authservice.client.StudentClient;
import com.project.authservice.client.UniversityClient;
import com.project.authservice.exception.AccessDeniedException;
import com.project.authservice.model.company.CompanyRequest;
import com.project.authservice.model.company.CompanyType;
import com.project.authservice.model.dto.*;
import com.project.authservice.model.student.DegreeEnum;
import com.project.authservice.model.student.StudentRequest;
import com.project.authservice.model.types.UserRole;
import com.project.authservice.model.university.UniversityRequest;
import com.project.authservice.model.university.UniversityType;
import com.project.authservice.service.EmailService;
import com.project.authservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.authservice.service.AuthService;

import java.math.BigDecimal;
import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@EnableFeignClients
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
    public ResponseEntity<AuthResponse> companyRegister(@RequestBody RegisterRequest request,
                                                        @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.ADMIN));

        UserDto userDto = authService.register(request);
        companyClient.createCompany(new CompanyRequest("", userDto.getId(), CompanyType.NONE, "", "", "", "", 0));
        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(new AuthResponse());

    }

    @DeleteMapping("/company/delete/{id}")
    public ResponseEntity<HttpStatus> companyDelete(@PathVariable Long id,
                                                      @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.ADMIN));

        authService.delete(id);
        companyClient.deleteCompanyByOwnerId(id);

        return ResponseEntity.ok(HttpStatus.OK);

    }



    @PostMapping("/university/registration")
    public ResponseEntity<AuthResponse> universityRegister(@RequestBody RegisterRequest request,
                                                           @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.ADMIN));

        UserDto userDto = authService.register(request);
        universityClient.createUniversity(new UniversityRequest(userDto.getId(), "", UniversityType.NONE, "", "", "", 0, ""));
        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(new AuthResponse());

    }

    @DeleteMapping("/university/delete/{id}")
    public ResponseEntity<HttpStatus> universityDelete(@PathVariable Long id,
                                                       @RequestHeader("X-User-Role") UserRole role) {

        hasRole(role, List.of(UserRole.ADMIN));

        authService.delete(id);
        universityClient.deleteUniversityProfileByOwnerId(id);

        return ResponseEntity.ok(HttpStatus.OK);

    }

    @PostMapping("/student/registration")
    public ResponseEntity<AuthResponse> studentRegister(@RequestBody RegisterRequest request,
                                                        @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        UserDto userDto = authService.register(request);
        studentClient.createStudent(new StudentRequest(userDto.getId(), "", "", "", 0L, BigDecimal.ZERO, "", DegreeEnum.NONE, 0, 0));
        emailService.sendAccountRegistrationEmail(request.getEmail(), request.getUsername(), request.getPassword());

        return ResponseEntity.ok().body(new AuthResponse());

    }

    @DeleteMapping("/student/delete/{id}")
    public ResponseEntity<HttpStatus> studentDelete(@PathVariable Long id,
                                                        @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.UNIVERSITY));

        authService.delete(id);
        studentClient.deleteStudentByOwnerId(id);

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
