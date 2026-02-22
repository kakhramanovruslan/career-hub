package com.project.universityservice.mapper;

import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.entity.University;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link UniversityRequest} and {@link University} entity.
 * Also handles updates to an existing {@link University} entity based on a {@link UniversityRequest}.
 * Uses MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring")
public interface UniversityRequestMapper {

    /**
     * Converts a {@link University} entity to a {@link UniversityRequest}.
     *
     * @param university the {@link University} entity to convert.
     * @return the corresponding {@link UniversityRequest}.
     */
    UniversityRequest toDto(University university);

    /**
     * Converts a {@link UniversityRequest} to a {@link University} entity.
     *
     * @param universityRequest the {@link UniversityRequest} to convert.
     * @return the corresponding {@link University} entity.
     */
    University toEntity(UniversityRequest universityRequest);

    /**
     * Updates an existing {@link University} entity with values from a {@link UniversityRequest}.
     *
     * @param request the {@link UniversityRequest} containing the new values.
     * @param university the existing {@link University} entity to update.
     * @return the updated {@link University} entity.
     */
    University updateUniversityFromRequest(UniversityRequest request, @MappingTarget University university);
}
