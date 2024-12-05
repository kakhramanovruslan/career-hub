package com.project.universityservice.service.impl;

import com.project.universityservice.client.StudentClient;
import com.project.universityservice.exception.AccessDeniedException;
import com.project.universityservice.model.dto.*;
import com.project.universityservice.exception.StudentNotFoundException;
import com.project.universityservice.exception.UniversityNotFoundException;
import com.project.universityservice.mapper.UniversityDtoMapper;
import com.project.universityservice.mapper.UniversityRequestMapper;
import com.project.universityservice.model.entity.University;
import com.project.universityservice.model.enums.UniversityType;
//import com.project.universityservice.producer.EmailProducer;
import com.project.universityservice.repository.UniversityRepository;
import com.project.universityservice.service.UniversityService;
import com.project.universityservice.util.ExceptionMessages;
import com.project.universityservice.util.UniversitySpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
//    private final EmailProducer emailProducer;

    @Override
    public UniversityDto findUniversityByOwnerId(Long ownerId) {
        Optional<University> university = universityRepository.findUniversityByOwnerId(ownerId);
        if (university.isPresent()) {
            return universityDtoMapper.toDto(university.get());
        } else {
            throw new UniversityNotFoundException("University not found for ownerId: " + ownerId);
        }
    }

    @Override
    public UniversityDto createUniversity(UniversityRequest universityRequest) {
        University university = universityRepository.save(universityRequestMapper.toEntity(universityRequest));
        UniversityDto universityDto = universityDtoMapper.toDto(university);
        log.info("Adding university with id {} to the database", universityDto.getId());
        return universityDto;
    }

    @Override
    public void updateUniversityByOwnerId(Long ownerId, UniversityRequest universityRequest, Long userId) {
        Optional<University> university = universityRepository.findUniversityByOwnerId(ownerId);
        if(university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        isOwner(userId, ownerId);
        universityRepository.save(universityRequestMapper.updateUniversityFromRequest(universityRequest, university.get()));
        log.info("Updating university with id {}", university.get().getId());
    }

    @Override
    @Transactional
    public void deleteUniversityByOwnerId(Long userId) {
        Optional<University> university = universityRepository.findUniversityByOwnerId(userId);
        if(university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        isOwner(userId, university.get().getOwnerId());
        universityRepository.deleteByOwnerId(userId);
        log.info("University with id {} has been deleted", userId);
    }

    @Override
    public StudentRequest createStudentProfile(StudentRequest studentRequest, String token) {
        return studentClient.createStudent(studentRequest, token);
    }

    @Override
    @Cacheable(value = "feignCache", cacheManager = "cacheManager", key = "#id")
    public List<StudentDto> findStudentByUniversityId(Long id, int page, int size, String token) {
        return studentClient.findStudentByUniversityId(id, page, size, token);
    }

    @Override
    public void deleteStudentById(Long id, String token) {
        findStudentOrThrow(id, token);
        studentClient.deleteStudentById(id, token);
        log.info("Student with id {} has been deleted [from University-Service]", id);
    }

    @Override
    public void updateStudentById(Long id, StudentRequest studentRequest, String token) {
        findStudentOrThrow(id, token);
        studentClient.updateStudentById(id, studentRequest, token);
        log.info("Updating student with id {}", id);
    }

    @Override
    public Page<UniversityDto> findByFilter(String name, UniversityType type, String location, Pageable pageable) {
        Page<University> universities = universityRepository.findAll(UniversitySpecification.withFilters(name, type, location), pageable);
        return universities.map(universityDtoMapper::toDto);
    }

    private University findUniversityOrThrow(Long id) throws UniversityNotFoundException {
        Optional<University> university = universityRepository.findById(id);
        if(university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        return university.get();
    }

    private StudentRequest findStudentOrThrow(Long id, String token) throws StudentNotFoundException {
        Optional<StudentRequest> student = Optional.ofNullable(studentClient.findStudentById(id, token));
        if(student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        return student.get();
    }

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
