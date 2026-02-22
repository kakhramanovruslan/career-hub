package com.project.studentservice.mapper;

import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.entity.Student;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between {@link Student} entity and {@link StudentDto}.
 * Utilizes MapStruct to automatically generate the implementation.
 */
@Mapper(componentModel = "spring")
public interface StudentDtoMapper {

    /**
     * Converts a {@link Student} entity to a {@link StudentDto}.
     *
     * @param student the student entity to convert.
     * @return the corresponding {@link StudentDto}.
     */
    StudentDto toDto(Student student);

    /**
     * Converts a {@link StudentDto} to a {@link Student} entity.
     *
     * @param studentDto the student DTO to convert.
     * @return the corresponding {@link Student} entity.
     */
    Student toEntity(StudentDto studentDto);
}
