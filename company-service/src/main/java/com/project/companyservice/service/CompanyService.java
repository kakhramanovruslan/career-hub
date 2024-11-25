package com.project.companyservice.service;

import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.model.enums.CompanyType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyDto findCompanyById(Long id);

    CompanyDto createCompany(CompanyRequest companyRequest);

    void updateCompanyById(Long id, CompanyRequest companyRequest, Long userId);

    void deleteCompanyById(Long id, Long userId);

    List<CompanyDto> findByFilter(String name, CompanyType type, String location, String industry, Pageable pageable);
}
