package com.project.companyservice.mapper;

import com.project.companyservice.model.dto.CompanyRequest;
import com.project.companyservice.model.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyRequestMapper {
    CompanyRequest toDto(Company company);

    Company toEntity(CompanyRequest companyDto);

    Company updateCompanyFromRequest(CompanyRequest request, @MappingTarget Company company);
}
