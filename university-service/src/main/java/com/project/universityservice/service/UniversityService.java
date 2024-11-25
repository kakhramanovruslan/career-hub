package com.project.universityservice.service;

import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.enums.UniversityType;

import java.util.List;

public interface UniversityService {
    UniversityDto findUniversityById(Long id);

    UniversityDto createUniversity(UniversityRequest universityRequest);

    void updateUniversityById(Long id, UniversityRequest universityRequest, Long userId);

    void deleteUniversityById(Long id, Long userId);

    StudentRequest createStudent(StudentRequest studentRequest);

    void deleteStudentById(Long id);

    void updateStudentById(Long id, StudentRequest studentRequest);

    List<UniversityDto> findByFilter(String name, UniversityType type, String location);
    List<StudentDto> findStudentByUniversityId(Long id);
}
