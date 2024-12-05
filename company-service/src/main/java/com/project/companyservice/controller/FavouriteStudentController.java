package com.project.companyservice.controller;

import com.project.companyservice.exception.AccessDeniedException;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.model.entity.FavouriteStudent;
import com.project.companyservice.model.enums.UserRole;
import com.project.companyservice.service.FavouriteStudentService;
import com.project.companyservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/company")
@RestController
@RequiredArgsConstructor
public class FavouriteStudentController {
    private final FavouriteStudentService favStudentService;

    @PostMapping("/favouriteStudent/{id}")
    public ResponseEntity<FavouriteStudent> addFavouriteStudent(@PathVariable Long id,
                                                                @RequestParam Long studentOwnerId,
                                                                @RequestHeader("X-User-Role") UserRole role,
                                                                @RequestHeader("X-User-Id") Long userId) {
        hasRole(role, List.of(UserRole.COMPANY));
        favStudentService.addFavouriteStudent(id, studentOwnerId, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/favouriteStudent/{id}")
    public ResponseEntity<FavouriteStudent> deleteFavouriteStudent(@PathVariable Long id,
                                                                   @RequestParam Long studentOwnerId,
                                                                   @RequestHeader("X-User-Role") UserRole role,
                                                                   @RequestHeader("X-User-Id") Long userId) {
        hasRole(role, List.of(UserRole.COMPANY));
        favStudentService.deleteFavouriteStudent(id, studentOwnerId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/favouriteStudent/{id}")
    public ResponseEntity<List<Long>> getFavouriteStudentsByOwnerId(@PathVariable Long id,
                                                                                @RequestHeader("X-User-Role") UserRole role,
                                                                                @RequestHeader("X-User-Id") Long userId) {
        hasRole(role, List.of(UserRole.COMPANY));
        return ResponseEntity.ok().body(favStudentService.getFavouriteStudentsByOwnerId(id, userId));
    }

    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
