package com.project.companyservice.service;

import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.model.enums.CompanyType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyDto findCompanyByOwnerId(Long ownerId);

    CompanyDto createCompany(CompanyRequest companyRequest);

    void updateCompanyByOwnerId(Long ownerId, CompanyRequest companyRequest, Long userId);

    void deleteCompanyByOwnerId(Long userId);

    Page<CompanyDto> findByFilter(String name, CompanyType type, String location, String industry, Pageable pageable);
}
