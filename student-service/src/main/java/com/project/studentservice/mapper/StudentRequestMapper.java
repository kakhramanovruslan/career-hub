package com.project.studentservice.mapper;

import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Mapper interface for converting between {@link StudentRequest} and {@link Student}.
 * Provides methods for creating new entities and updating existing ones.
 */
@Mapper(componentModel = "spring")
public interface StudentRequestMapper {

    /**
     * Converts a {@link Student} entity to a {@link StudentRequest} DTO.
     *
     * @param student the student entity to convert.
     * @return the corresponding {@link StudentRequest} DTO.
     */
    StudentRequest toDto(Student student);

    /**
     * Converts a {@link StudentRequest} DTO to a {@link Student} entity.
     *
     * @param studentDto the student request DTO to convert.
     * @return the corresponding {@link Student} entity.
     */
    Student toEntity(StudentRequest studentDto);

    /**
     * Updates the existing {@link Student} entity with values from the given {@link StudentRequest}.
     *
     * @param request the {@link StudentRequest} containing the updated information.
     * @param student the {@link Student} entity to update.
     * @return the updated {@link Student} entity.
     */
    Student updateStudentFromRequest(StudentRequest request, @MappingTarget Student student);
}
