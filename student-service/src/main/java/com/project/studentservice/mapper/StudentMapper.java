package com.project.studentservice.mapper;

import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDto(Student student);

    Student toEntity(StudentDto studentDto);
}
