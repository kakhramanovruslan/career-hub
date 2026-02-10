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

/**
 * Controller responsible for handling requests related to the management of favourite students for a company.
 * This includes adding, removing, and retrieving favourite students.
 */
@RequestMapping("/company")
@RestController
@RequiredArgsConstructor
public class FavouriteStudentController {

    private final FavouriteStudentService favStudentService;

    /**
     * Adds a student to the list of favourite students for a company.
     *
     * @param id The ID of the company.
     * @param studentOwnerId The ID of the student to be added to the favourites.
     * @param role The role of the current user (provided via the request header).
     * @param userId The ID of the current user (provided via the request header).
     * @return A response indicating the student has been added to the favourites.
     * @throws AccessDeniedException If the user does not have the necessary role (COMPANY).
     */
    @PostMapping("/favouriteStudent/{id}")
    public ResponseEntity<FavouriteStudent> addFavouriteStudent(@PathVariable Long id,
                                                                @RequestParam Long studentOwnerId,
                                                                @RequestHeader("X-User-Role") UserRole role,
                                                                @RequestHeader("X-User-Id") Long userId) {
        hasRole(role, List.of(UserRole.COMPANY));
        favStudentService.addFavouriteStudent(id, studentOwnerId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Removes a student from the list of favourite students for a company.
     *
     * @param id The ID of the company.
     * @param studentOwnerId The ID of the student to be removed from the favourites.
     * @param role The role of the current user (provided via the request header).
     * @param userId The ID of the current user (provided via the request header).
     * @return A response indicating the student has been removed from the favourites.
     * @throws AccessDeniedException If the user does not have the necessary role (COMPANY).
     */
    @DeleteMapping("/favouriteStudent/{id}")
    public ResponseEntity<FavouriteStudent> deleteFavouriteStudent(@PathVariable Long id,
                                                                   @RequestParam Long studentOwnerId,
                                                                   @RequestHeader("X-User-Role") UserRole role,
                                                                   @RequestHeader("X-User-Id") Long userId) {
        hasRole(role, List.of(UserRole.COMPANY));
        favStudentService.deleteFavouriteStudent(id, studentOwnerId, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the list of favourite students for a company.
     *
     * @param id The ID of the company.
     * @param role The role of the current user (provided via the request header).
     * @param userId The ID of the current user (provided via the request header).
     * @return A list of student IDs who are marked as favourite by the company.
     * @throws AccessDeniedException If the user does not have the necessary role (COMPANY).
     */
    @GetMapping("/favouriteStudent/{id}")
    public ResponseEntity<List<Long>> getFavouriteStudentsByOwnerId(@PathVariable Long id,
                                                                    @RequestHeader("X-User-Role") UserRole role,
                                                                    @RequestHeader("X-User-Id") Long userId) {
        hasRole(role, List.of(UserRole.COMPANY));
        return ResponseEntity.ok().body(favStudentService.getFavouriteStudentsByOwnerId(id, userId));
    }

    /**
     * Checks if the current user has the required role to perform the requested action.
     *
     * @param currentRole The role of the current user.
     * @param requiredRoles A list of required roles for the action.
     * @throws AccessDeniedException If the current user does not have the required role.
     */
    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
