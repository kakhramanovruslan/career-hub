package com.project.universityservice.mapper;

import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.entity.University;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UniversityRequestMapper {
    UniversityRequest toDto(University university);

    University toEntity(UniversityRequest universityRequest);

    University updateUniversityFromRequest(UniversityRequest request, @MappingTarget University university);
}
