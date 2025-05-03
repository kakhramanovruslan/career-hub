package com.project.companyservice.mapper;

import com.project.companyservice.model.dto.CompanyDto;
import com.project.companyservice.model.entity.Company;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link Company} entities and {@link CompanyDto} data transfer objects (DTO).
 * This interface uses MapStruct to automatically generate the necessary mapping code.
 *
 * The mapper allows easy conversion between the internal representation of a company (entity) and the data transfer object
 * used for communication (DTO). It helps ensure consistency and reduces boilerplate code in the application.
 */
@Mapper(componentModel = "spring")
public interface CompanyDtoMapper {

    /**
     * Converts a {@link Company} entity to a {@link CompanyDto}.
     *
     * @param company The company entity to be converted.
     * @return A {@link CompanyDto} representing the given company entity.
     */
    CompanyDto toDto(Company company);

    /**
     * Converts a {@link CompanyDto} to a {@link Company} entity.
     *
     * @param companyDto The company DTO to be converted.
     * @return A {@link Company} entity representing the given company DTO.
     */
    Company toEntity(CompanyDto companyDto);
}
