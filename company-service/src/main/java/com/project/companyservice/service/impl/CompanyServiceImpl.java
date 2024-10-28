package com.project.companyservice.service.impl;

import com.project.companyservice.dto.CompanyDto;
import com.project.companyservice.dto.CompanyRequest;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.mapper.CompanyDtoMapper;
import com.project.companyservice.mapper.CompanyRequestMapper;
import com.project.companyservice.model.Company;
import com.project.companyservice.repository.CompanyRepository;
import com.project.companyservice.service.CompanyService;
import com.project.companyservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyDtoMapper companyDtoMapper;
    private final CompanyRequestMapper companyRequestMapper;

    @Override
    public CompanyDto findCompanyById(Long id) {
        Company company = findCompanyOrThrow(id);
        return companyDtoMapper.toDto(company);
    }

    @Override
    public CompanyDto createCompany(CompanyRequest companyRequest) {
        Company company = companyRepository.save(companyRequestMapper.toEntity(companyRequest));
        CompanyDto companyDto = companyDtoMapper.toDto(company);
        log.info("Adding company with id {} to the database", companyDto.getId());
        return companyDto;
    }

    @Override
    public void updateCompanyById(Long id, CompanyRequest companyRequest) {
        Company company = findCompanyOrThrow(id);
        companyRepository.save(companyRequestMapper.updateCompanyFromRequest(companyRequest, company));
        log.info("Updating company with id {}", id);
    }

    @Override
    public void deleteCompanyById(Long id) {
        findCompanyOrThrow(id);
        companyRepository.deleteById(id);
        log.info("Company with id {} has been deleted", id);
    }

    private Company findCompanyOrThrow(Long id) throws CompanyNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()) throw new CompanyNotFoundException(ExceptionMessages.COMPANY_NOT_FOUND);
        return company.get();
    }
}
