package com.project.companyservice.mapper;

import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.model.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper for converting between {@link CompanyRequest} and {@link Company}.
 */
@Mapper(componentModel = "spring")
public interface CompanyRequestMapper {

    /**
     * Converts {@link Company} to {@link CompanyRequest}.
     */
    CompanyRequest toDto(Company company);

    /**
     * Converts {@link CompanyRequest} to {@link Company}.
     */
    Company toEntity(CompanyRequest companyDto);

    /**
     * Updates {@link Company} with data from {@link CompanyRequest}.
     */
    Company updateCompanyFromRequest(CompanyRequest request, @MappingTarget Company company);
}
