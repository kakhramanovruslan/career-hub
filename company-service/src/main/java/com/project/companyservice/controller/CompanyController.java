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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/search")
    public ResponseEntity<List<CompanyDto>> getCompanies(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) CompanyType type,
                                                         @RequestParam(required = false) String location,
                                                         @RequestParam(required = false) String industry,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(companyService.findByFilter(name, type, location, industry, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) throws CompanyNotFoundException {
        return ResponseEntity.ok().body(companyService.findCompanyById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyDto> createCompanyProfile(@RequestBody CompanyRequest companyRequest,
                                                           @RequestHeader("X-User-Role") UserRole role){
        hasRole(role, List.of(UserRole.ADMIN));
        return ResponseEntity.status(201).body(companyService.createCompany(companyRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateCompanyById(@PathVariable Long id,
                                                  @RequestBody CompanyRequest companyRequest,
                                                  @RequestHeader("X-User-Role") UserRole role,
                                                  @RequestHeader("X-User-Id") Long userId)
            throws CompanyNotFoundException {
        hasRole(role, List.of(UserRole.COMPANY));
        companyService.updateCompanyById(id, companyRequest, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteCompanyByOwnerId(@PathVariable Long userId,
                                                  @RequestHeader("X-User-Role") UserRole role)
            throws CompanyNotFoundException{
        hasRole(role, List.of(UserRole.ADMIN));
        companyService.deleteCompanyByOwnerId(userId);
        return ResponseEntity.ok().build();
    }

    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
