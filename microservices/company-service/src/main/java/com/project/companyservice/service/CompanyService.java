package com.project.companyservice.service;

import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.model.enums.CompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for managing companies.
 */
public interface CompanyService {

    /**
     * Finds a company by its owner's ID.
     */
    CompanyDto findCompanyByOwnerId(Long ownerId);

    /**
     * Creates a new company.
     */
    CompanyDto createCompany(CompanyRequest companyRequest);

    /**
     * Updates an existing company by its owner's ID.
     */
    void updateCompanyByOwnerId(Long ownerId, CompanyRequest companyRequest, Long userId);

    /**
     * Deletes a company by its owner's ID.
     */
    void deleteCompanyByOwnerId(Long userId);

    /**
     * Finds companies by filtering based on provided attributes.
     */
    Page<CompanyDto> findByFilter(String name, CompanyType type, String location, String industry, Pageable pageable);
}
