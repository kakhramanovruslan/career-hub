package com.project.studentservice.service;


import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.types.DegreeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    StudentDto findStudentByOwnerId(Long id) throws SQLException;

    StudentDto addStudent(StudentRequest studentRequest) throws SQLException;

    void deleteStudentByOwnerId(Long id, Long userId) throws SQLException;

    List<StudentDto> findStudentByUniversityId(Long id, Pageable pageable);

    void updateStudentProfileByOwnerId(Long id, StudentRequest studentRequest, Long userId) throws SQLException;

    Page<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degreeEnum, Integer currentYear, Double minGpa, Double maxGpa, Pageable pageable);
}
