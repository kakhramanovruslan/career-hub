package com.project.universityservice.service;

import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.enums.UniversityType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UniversityService {
    UniversityDto findUniversityById(Long id);

    UniversityDto createUniversity(UniversityRequest universityRequest);

    void updateUniversityById(Long id, UniversityRequest universityRequest, Long userId);

    void deleteUniversityByOwnerId(Long userId);

    StudentRequest createStudentProfile(StudentRequest studentRequest, String token);

    void deleteStudentById(Long id, String token);

    void updateStudentById(Long id, StudentRequest studentRequest, String token);

    List<UniversityDto> findByFilter(String name, UniversityType type, String location, Pageable pageable);
    List<StudentDto> findStudentByUniversityId(Long id, int page, int size, String token);
}
