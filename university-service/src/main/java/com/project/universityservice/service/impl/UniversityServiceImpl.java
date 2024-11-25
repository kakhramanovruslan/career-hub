package com.project.universityservice.service.impl;

import com.project.universityservice.client.StudentClient;
import com.project.universityservice.exception.AccessDeniedException;
import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.exception.StudentNotFoundException;
import com.project.universityservice.exception.UniversityNotFoundException;
import com.project.universityservice.mapper.UniversityDtoMapper;
import com.project.universityservice.mapper.UniversityRequestMapper;
import com.project.universityservice.model.entity.University;
import com.project.universityservice.model.enums.UniversityType;
import com.project.universityservice.repository.UniversityRepository;
import com.project.universityservice.service.UniversityService;
import com.project.universityservice.util.ExceptionMessages;
import com.project.universityservice.util.UniversitySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void updateUniversityById(Long id, UniversityRequest universityRequest, Long userId) {
        University university = findUniversityOrThrow(id);
        isOwner(userId, university.getOwnerId());
        universityRepository.save(universityRequestMapper.updateUniversityFromRequest(universityRequest, university));
        log.info("Updating university with id {}", id);
    }

    @Override
    public void deleteUniversityById(Long id, Long userId) {
        University university = findUniversityOrThrow(id);
        isOwner(userId, university.getOwnerId());
        universityRepository.deleteById(id);
        log.info("University with id {} has been deleted", id);
    }

    @Override
    public StudentRequest createStudent(StudentRequest studentRequest) {
        return studentClient.createStudent(studentRequest);
    }

    @Override
    @Cacheable(value = "feignCache", cacheManager = "cacheManager", key = "#id")
    public List<StudentDto> findStudentByUniversityId(Long id) {
        return studentClient.findStudentByUniversityId(id);
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

    @Override
    public List<UniversityDto> findByFilter(String name, UniversityType type, String location) {
        List<University> universities = universityRepository.findAll(UniversitySpecification.withFilters(name, type, location));
        return universities.stream()
                .map(universityDtoMapper::toDto)
                .toList();
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

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
