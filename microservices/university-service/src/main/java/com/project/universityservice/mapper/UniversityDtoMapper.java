package com.project.universityservice.mapper;

import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.entity.University;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link University} entity and {@link UniversityDto}.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface UniversityDtoMapper {

    /**
     * Converts a {@link University} entity to a {@link UniversityDto}.
     *
     * @param university the {@link University} entity to convert.
     * @return the corresponding {@link UniversityDto}.
     */
    UniversityDto toDto(University university);

    /**
     * Converts a {@link UniversityDto} to a {@link University} entity.
     *
     * @param universityDto the {@link UniversityDto} to convert.
     * @return the corresponding {@link University} entity.
     */
    University toEntity(UniversityDto universityDto);
}
