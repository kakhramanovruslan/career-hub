package com.project.universityservice.service;

import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.enums.UniversityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UniversityService {
    UniversityDto findUniversityByOwnerId(Long ownerId);

    UniversityDto createUniversity(UniversityRequest universityRequest);

    void updateUniversityByOwnerId(Long ownerId, UniversityRequest universityRequest, Long userId);

    void deleteUniversityByOwnerId(Long userId);

    StudentRequest createStudentProfile(StudentRequest studentRequest, String token);

    void deleteStudentById(Long id, String token);

    void updateStudentById(Long id, StudentRequest studentRequest, String token);

    Page<UniversityDto> findByFilter(String name, UniversityType type, String location, Pageable pageable);
    List<StudentDto> findStudentByUniversityId(Long id, int page, int size, String token);
}
