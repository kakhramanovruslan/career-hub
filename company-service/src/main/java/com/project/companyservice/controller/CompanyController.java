package com.project.companyservice.controller;

import com.project.companyservice.exception.AccessDeniedException;
import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.model.enums.CompanyType;
import com.project.companyservice.model.enums.UserRole;
import com.project.companyservice.service.CompanyService;
import com.project.companyservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for handling company-related requests in the company service.
 * This includes searching for companies, creating, updating, and deleting company profiles.
 */
@RequiredArgsConstructor
@RequestMapping("/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Retrieves a paginated list of companies filtered by the provided search parameters.
     *
     * @param name     The name of the company (optional).
     * @param type     The type of the company (optional).
     * @param location The location of the company (optional).
     * @param industry The industry of the company (optional).
     * @param page     The page number for pagination (default is 0).
     * @param size     The number of results per page (default is 10).
     * @return A paginated response with the list of companies matching the filter criteria.
     */
    @GetMapping("/search")
    public ResponseEntity<Page<CompanyDto>> getCompanies(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) CompanyType type,
                                                         @RequestParam(required = false) String location,
                                                         @RequestParam(required = false) String industry,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(companyService.findByFilter(name, type, location, industry, pageable));
    }

    /**
     * Retrieves a company by its owner's ID.
     *
     * @param ownerId The owner ID of the company.
     * @return A response containing the company details.
     * @throws CompanyNotFoundException If the company with the given owner ID is not found.
     */
    @GetMapping("/{ownerId}")
    public ResponseEntity<CompanyDto> getCompanyByOwnerId(@PathVariable Long ownerId) throws CompanyNotFoundException {
        return ResponseEntity.ok().body(companyService.findCompanyByOwnerId(ownerId));
    }

    /**
     * Creates a new company profile.
     *
     * @param companyRequest The request body containing the company details.
     * @param role           The role of the current user (provided via the request header).
     * @return A response containing the created company details.
     * @throws AccessDeniedException If the user does not have the necessary role (ADMIN).
     */
    @PostMapping("/create")
    public ResponseEntity<CompanyDto> createCompanyProfile(@RequestBody CompanyRequest companyRequest,
                                                           @RequestHeader("X-User-Role") UserRole role) {
        hasRole(role, List.of(UserRole.ADMIN));
        return ResponseEntity.status(201).body(companyService.createCompany(companyRequest));
    }

    /**
     * Updates the company profile by its owner's ID.
     *
     * @param ownerId       The owner ID of the company.
     * @param companyRequest The request body containing the updated company details.
     * @param role           The role of the current user (provided via the request header).
     * @param userId         The user ID of the current user (provided via the request header).
     * @return A response indicating the update status.
     * @throws CompanyNotFoundException If the company with the given owner ID is not found.
     * @throws AccessDeniedException If the user does not have the necessary role (COMPANY).
     */
    @PutMapping("/update/{ownerId}")
    public ResponseEntity<Void> updateCompanyByOwnerId(@PathVariable Long ownerId,
                                                       @RequestBody CompanyRequest companyRequest,
                                                       @RequestHeader("X-User-Role") UserRole role,
                                                       @RequestHeader("X-User-Id") Long userId)
            throws CompanyNotFoundException {
        hasRole(role, List.of(UserRole.COMPANY));
        companyService.updateCompanyByOwnerId(ownerId, companyRequest, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes the company profile by its owner's ID.
     *
     * @param userId The user ID of the company owner.
     * @param role   The role of the current user (provided via the request header).
     * @return A response indicating the deletion status.
     * @throws CompanyNotFoundException If the company with the given user ID is not found.
     * @throws AccessDeniedException If the user does not have the necessary role (ADMIN).
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCompanyByOwnerId(@PathVariable Long userId,
                                                       @RequestHeader("X-User-Role") UserRole role)
            throws CompanyNotFoundException {
        hasRole(role, List.of(UserRole.ADMIN));
        companyService.deleteCompanyByOwnerId(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks if the current user's role is allowed to perform the requested action.
     *
     * @param currentRole The role of the current user.
     * @param requiredRoles A list of required roles for the action.
     * @throws AccessDeniedException If the current user does not have the required role.
     */
    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
