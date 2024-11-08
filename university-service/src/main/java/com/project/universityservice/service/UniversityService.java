package com.project.universityservice.service;

import com.project.universityservice.dto.StudentRequest;
import com.project.universityservice.dto.UniversityRequest;
import com.project.universityservice.dto.UniversityDto;
import com.project.universityservice.model.University;
import com.project.universityservice.model.enums.UniversityType;

import java.sql.SQLException;
import java.util.List;

public interface UniversityService {
    UniversityDto findUniversityById(Long id);

    UniversityDto createUniversity(UniversityRequest universityRequest);

    void updateUniversityById(Long id, UniversityRequest universityRequest);

    void deleteUniversityById(Long id);

    StudentRequest createStudent(StudentRequest studentRequest);

    void deleteStudentById(Long id);

    void updateStudentById(Long id, StudentRequest studentRequest);

    List<University> findByFilter(String name, UniversityType type, String location);
}
