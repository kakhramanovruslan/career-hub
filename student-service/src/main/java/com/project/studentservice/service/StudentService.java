package com.project.studentservice.service;


import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.types.DegreeEnum;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

public interface StudentService {

    StudentDto findStudentById(Long id) throws SQLException;

    StudentDto addStudent(StudentRequest studentRequest) throws SQLException;

    void deleteStudentById(Long id, Long userId) throws SQLException;

    List<StudentDto> findStudentByUniversityId(Long id, Pageable pageable);

<<<<<<< HEAD
    void updateStudentById(Long id, StudentRequest studentRequest, Long userId) throws SQLException;
    List<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degreeEnum, Integer currentYear, Double minGpa, Double maxGpa);
=======
    void updateStudentById(Long id, StudentRequest studentRequest) throws SQLException;
    List<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degreeEnum, Integer currentYear, Double minGpa, Double maxGpa, Pageable pageable);
>>>>>>> c04cca77a56cb248d993aeb8269e51834f881132
}
