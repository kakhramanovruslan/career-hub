package com.project.studentservice.service;


import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;

import java.sql.SQLException;

public interface StudentService {

    StudentDto findStudentById(Long id) throws SQLException;

    StudentDto addStudent(StudentRequest studentRequest) throws SQLException;

    void deleteStudentById(Long id) throws SQLException;

    void updateStudentById(Long id, StudentRequest studentRequest) throws SQLException;
}
