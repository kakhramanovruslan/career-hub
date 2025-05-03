package com.project.universityservice.controller;

import com.project.universityservice.exception.AccessDeniedException;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.exception.UniversityNotFoundException;
import com.project.universityservice.model.enums.UniversityType;
import com.project.universityservice.model.enums.UserRole;
import com.project.universityservice.service.UniversityService;
import com.project.universityservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle requests related to universities.
 * Provides endpoints for searching, creating, updating, and deleting university profiles.
 * Also includes methods for validating user roles and handling access control.
 */
@RequiredArgsConstructor
@RequestMapping("/university")
@RestController
public class UniversityController {

    private final UniversityService universityService;

    /**
     * Searches universities based on various filters and returns a paginated list.
     *
     * @param name the name of the university (optional).
     * @param type the type of the university (optional).
     * @param location the location of the university (optional).
     * @param page the page number for pagination (default is 0).
     * @param size the number of items per page (default is 10).
     * @return a {@link ResponseEntity} containing the paginated list of universities.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<UniversityDto>> getUniversities(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) UniversityType type,
                                                               @RequestParam(required = false) String location,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(universityService.findByFilter(name, type, location, pageable));
    }

    /**
     * Fetches a university's details by its owner ID.
     *
     * @param ownerId the ID of the university owner.
     * @return a {@link ResponseEntity} containing the university details.
     * @throws UniversityNotFoundException if the university is not found.
     */
    @GetMapping("/{ownerId}")
    public ResponseEntity<UniversityDto> getUniversityByOwnerId(@PathVariable Long ownerId) throws UniversityNotFoundException {
        return ResponseEntity.ok().body(universityService.findUniversityByOwnerId(ownerId));
    }

    /**
     * Creates a new university profile.
     *
     * @param universityRequest the details of the university to be created.
     * @param role the role of the current user (must be ADMIN).
     * @return a {@link ResponseEntity} containing the created university details.
     * @throws AccessDeniedException if the user does not have ADMIN role.
     */
    @PostMapping("/create")
    public ResponseEntity<UniversityDto> createUniversityProfile(@RequestBody UniversityRequest universityRequest,
                                                                 @RequestHeader("X-User-Role") UserRole role){
        hasRole(role, List.of(UserRole.ADMIN));
        return ResponseEntity.status(201).body(universityService.createUniversity(universityRequest));
    }

    /**
     * Updates an existing university profile by its owner ID.
     *
     * @param ownerId the ID of the university owner.
     * @param universityRequest the updated details of the university.
     * @param role the role of the current user (must be UNIVERSITY).
     * @param userId the ID of the user making the update.
     * @return a {@link ResponseEntity} indicating the result of the update.
     * @throws UniversityNotFoundException if the university is not found.
     * @throws AccessDeniedException if the user does not have UNIVERSITY role.
     */
    @PutMapping("/update/{ownerId}")
    public ResponseEntity<Void> updateUniversityProfileByOwnerId(@PathVariable Long ownerId,
                                                                 @RequestBody UniversityRequest universityRequest,
                                                                 @RequestHeader("X-User-Role") UserRole role,
                                                                 @RequestHeader("X-User-Id") Long userId)
            throws UniversityNotFoundException {
        hasRole(role, List.of(UserRole.UNIVERSITY));
        universityService.updateUniversityByOwnerId(ownerId, universityRequest, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a university profile by its owner ID.
     *
     * @param userId the ID of the user making the delete request.
     * @param role the role of the current user (must be ADMIN).
     * @return a {@link ResponseEntity} indicating the result of the delete operation.
     * @throws UniversityNotFoundException if the university is not found.
     * @throws AccessDeniedException if the user does not have ADMIN role.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUniversityProfileByOwnerId(@PathVariable Long userId,
                                                                 @RequestHeader("X-User-Role") UserRole role)
            throws UniversityNotFoundException{
        hasRole(role, List.of(UserRole.ADMIN));
        universityService.deleteUniversityByOwnerId(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Helper method to check if the current user has the required role.
     *
     * @param currentRole the role of the current user.
     * @param requiredRoles the list of required roles.
     * @throws AccessDeniedException if the user does not have the required role.
     */
    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
