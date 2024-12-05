package com.project.authservice.controller;

import com.project.authservice.model.dto.*;
import com.project.authservice.model.types.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.project.authservice.service.AuthService;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/company/registration")
    public ResponseEntity<UserDto> companyRegister(@RequestBody RegisterRequest request,
                                                   @RequestHeader("X-User-Role") UserRole role,
                                                   @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.companyRegister(request, role, token));
    }

    @DeleteMapping("/company/delete/{id}")
    public ResponseEntity<HttpStatus> companyDelete(@PathVariable Long id,
                                                    @RequestHeader("X-User-Role") UserRole role,
                                                    @RequestHeader("Authorization") String token) {
        authService.companyDelete(id, role, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }



    @PostMapping("/university/registration")
    public ResponseEntity<UserDto> universityRegister(@RequestBody RegisterRequest request,
                                                      @RequestHeader("X-User-Role") UserRole role,
                                                      @RequestHeader("Authorization") String token){
        return ResponseEntity.ok(authService.registerUniversity(request, role, token));
    }

    @DeleteMapping("/university/delete/{id}")
    public ResponseEntity<HttpStatus> universityDelete(@PathVariable Long id,
                                                       @RequestHeader("X-User-Role") UserRole role,
                                                       @RequestHeader("Authorization") String token) {
        authService.universityDelete(id, role, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/student/registration")
    public ResponseEntity<UserDto> studentRegister(@RequestBody RegisterRequest request,
                                                   @RequestHeader("X-User-Role") UserRole role,
                                                   @RequestHeader("X-User-Id") Long userId,
                                                   @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.registerStudent(request, userId, role, token));
    }

    @DeleteMapping("/student/delete/{userId}")
    public ResponseEntity<HttpStatus> studentDelete(@PathVariable Long userId,
                                                    @RequestHeader("X-User-Role") UserRole role,
                                                    @RequestHeader("Authorization") String token) {
        authService.studentDelete(userId, role, token);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest user) {
        return ResponseEntity.ok().body(authService.login(user));
    }
}
