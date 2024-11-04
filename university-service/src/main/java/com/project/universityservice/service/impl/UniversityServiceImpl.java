package com.project.universityservice.service.impl;

import com.project.universityservice.client.StudentClient;
import com.project.universityservice.dto.StudentRequest;
import com.project.universityservice.dto.UniversityRequest;
import com.project.universityservice.dto.UniversityDto;
import com.project.universityservice.exception.StudentNotFoundException;
import com.project.universityservice.exception.UniversityNotFoundException;
import com.project.universityservice.mapper.UniversityDtoMapper;
import com.project.universityservice.mapper.UniversityRequestMapper;
import com.project.universityservice.model.University;
import com.project.universityservice.repository.UniversityRepository;
import com.project.universityservice.service.UniversityService;
import com.project.universityservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityDtoMapper universityDtoMapper;
    private final UniversityRequestMapper universityRequestMapper;
    private final StudentClient studentClient;

    @Override
    public UniversityDto findUniversityById(Long id) {
        University university = findUniversityOrThrow(id);
        return universityDtoMapper.toDto(university);
    }

    @Override
    public UniversityDto createUniversity(UniversityRequest universityRequest) {
        University university = universityRepository.save(universityRequestMapper.toEntity(universityRequest));
        UniversityDto universityDto = universityDtoMapper.toDto(university);
        log.info("Adding university with id {} to the database", universityDto.getId());
        return universityDto;
    }

    @Override
    public void updateUniversityById(Long id, UniversityRequest universityRequest) {
        University university = findUniversityOrThrow(id);
        universityRepository.save(universityRequestMapper.updateUniversityFromRequest(universityRequest, university));
        log.info("Updating university with id {}", id);
    }

    @Override
    public void deleteUniversityById(Long id) {
        findUniversityOrThrow(id);
        universityRepository.deleteById(id);
        log.info("University with id {} has been deleted", id);
    }

    @Override
    public StudentRequest createStudent(StudentRequest studentRequest) {
        return studentClient.createStudent(studentRequest);
    }

    @Override
    public void deleteStudentById(Long id) {
        findStudentOrThrow(id);
        studentClient.deleteStudentById(id);
        log.info("Student with id {} has been deleted [from University-Service]", id);
    }

    @Override
    public void updateStudentById(Long id, StudentRequest studentRequest) {
        findStudentOrThrow(id);
        studentClient.updateStudentById(id, studentRequest);
        log.info("Updating student with id {}", id);
    }

    private University findUniversityOrThrow(Long id) throws UniversityNotFoundException {
        Optional<University> university = universityRepository.findById(id);
        if(university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        return university.get();
    }

    private StudentRequest findStudentOrThrow(Long id) throws StudentNotFoundException {
        Optional<StudentRequest> student = Optional.ofNullable(studentClient.findStudentById(id));
        if(student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        return student.get();
    }
}
