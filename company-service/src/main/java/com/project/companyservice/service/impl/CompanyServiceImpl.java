package com.project.companyservice.service.impl;

import com.project.companyservice.exception.AccessDeniedException;
import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.mapper.CompanyDtoMapper;
import com.project.companyservice.mapper.CompanyRequestMapper;
import com.project.companyservice.model.entity.Company;
import com.project.companyservice.model.enums.CompanyType;
import com.project.companyservice.repository.CompanyRepository;
import com.project.companyservice.service.CompanyService;
import com.project.companyservice.util.CompanySpecification;
import com.project.companyservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public void updateCompanyById(Long id, CompanyRequest companyRequest, Long userId) {
        Company company = findCompanyOrThrow(id);
        isOwner(userId, company.getOwnerId());
        companyRepository.save(companyRequestMapper.updateCompanyFromRequest(companyRequest, company));
        log.info("Updating company with id {}", id);
    }

    @Override
    @Transactional
    public void deleteCompanyByOwnerId(Long userId) {
        Optional<Company> company = companyRepository.findCompanyByOwnerId(userId);
        if(company.isEmpty()) throw new CompanyNotFoundException(ExceptionMessages.COMPANY_NOT_FOUND);
        isOwner(userId, company.get().getOwnerId());
        companyRepository.deleteByOwnerId(userId);
        log.info("Company with id {} has been deleted", userId);
    }

    @Override
    public List<CompanyDto> findByFilter(String name, CompanyType type, String location, String industry, Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(CompanySpecification.withFilters(name, type, location, industry), pageable);
        return companies.stream()
                .map(companyDtoMapper::toDto)
                .toList();
    }

    private Company findCompanyOrThrow(Long id) throws CompanyNotFoundException {
        Optional<Company> company = companyRepository.findById(id);
        if(company.isEmpty()) throw new CompanyNotFoundException(ExceptionMessages.COMPANY_NOT_FOUND);
        return company.get();
    }

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
