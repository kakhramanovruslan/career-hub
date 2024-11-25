package com.project.companyservice.mapper;

import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyDtoMapper {
    CompanyDto toDto(Company company);

    Company toEntity(CompanyDto companyDto);
}
