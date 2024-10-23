package com.project.studentservice.mapper;

import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentRequestMapper {

    StudentRequest toDto(Student student);

    Student toEntity(StudentRequest studentDto);

    Student updateStudentFromRequest(StudentRequest request, @MappingTarget Student student);

}
