package com.project.universityservice.mapper;

import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.entity.University;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UniversityDtoMapper {

    UniversityDto toDto(University university);

    University toEntity(UniversityDto universityDto);
}
