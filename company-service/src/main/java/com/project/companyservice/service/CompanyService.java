package com.project.companyservice.service;

import com.project.companyservice.dto.CompanyDto;
import com.project.companyservice.dto.CompanyRequest;

public interface CompanyService {
    CompanyDto findCompanyById(Long id);

    CompanyDto createCompany(CompanyRequest companyRequest);

    void updateCompanyById(Long id, CompanyRequest companyRequest);

    void deleteCompanyById(Long id);

}
