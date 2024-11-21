package com.project.studentservice.service;


import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.types.DegreeEnum;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    StudentDto findStudentById(Long id) throws SQLException;

    StudentDto addStudent(StudentRequest studentRequest) throws SQLException;

    void deleteStudentById(Long id) throws SQLException;

    List<StudentDto> findStudentByUniversityId(Long id);

    void updateStudentById(Long id, StudentRequest studentRequest) throws SQLException;
    List<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degreeEnum, Integer currentYear, Double minGpa, Double maxGpa);
}
