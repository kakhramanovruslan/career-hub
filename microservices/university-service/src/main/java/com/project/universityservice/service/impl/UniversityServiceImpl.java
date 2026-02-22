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

/**
 * Implementation of the {@link UniversityService} interface. This service handles the
 * business logic related to universities and their associated students, including
 * creating, updating, retrieving, and deleting universities and students.
 * Also handles access control and interactions with the student service via Feign client.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;
    private final UniversityDtoMapper universityDtoMapper;
    private final UniversityRequestMapper universityRequestMapper;
    private final StudentClient studentClient;
//    private final EmailProducer emailProducer;

    /**
     * Finds a university by its owner ID.
     *
     * @param ownerId the ID of the university owner.
     * @return the {@link UniversityDto} of the found university.
     * @throws UniversityNotFoundException if the university is not found.
     */
    @Override
    public UniversityDto findUniversityByOwnerId(Long ownerId) {
        Optional<University> university = universityRepository.findUniversityByOwnerId(ownerId);
        if (university.isPresent()) {
            return universityDtoMapper.toDto(university.get());
        } else {
            throw new UniversityNotFoundException("University not found for ownerId: " + ownerId);
        }
    }

    /**
     * Creates a new university from the provided request data.
     *
     * @param universityRequest the data for creating the university.
     * @return the {@link UniversityDto} of the created university.
     */
    @Override
    public UniversityDto createUniversity(UniversityRequest universityRequest) {
        University university = universityRepository.save(universityRequestMapper.toEntity(universityRequest));
        UniversityDto universityDto = universityDtoMapper.toDto(university);
        log.info("Adding university with id {} to the database", universityDto.getId());
        return universityDto;
    }

    /**
     * Updates the university details for a given owner ID.
     *
     * @param ownerId           the ID of the university owner.
     * @param universityRequest the updated university data.
     * @param userId            the ID of the user attempting to update the university.
     * @throws UniversityNotFoundException if the university is not found.
     * @throws AccessDeniedException       if the user does not have permission to update the university.
     */
    @Override
    public void updateUniversityByOwnerId(Long ownerId, UniversityRequest universityRequest, Long userId) {
        Optional<University> university = universityRepository.findUniversityByOwnerId(ownerId);
        if (university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        isOwner(userId, ownerId);
        universityRepository.save(universityRequestMapper.updateUniversityFromRequest(universityRequest, university.get()));
        log.info("Updating university with id {}", university.get().getId());
    }

    /**
     * Deletes a university by its owner ID.
     *
     * @param userId the ID of the user requesting the deletion.
     * @throws UniversityNotFoundException if the university is not found.
     * @throws AccessDeniedException       if the user does not have permission to delete the university.
     */
    @Override
    @Transactional
    public void deleteUniversityByOwnerId(Long userId) {
        Optional<University> university = universityRepository.findUniversityByOwnerId(userId);
        if (university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        isOwner(userId, university.get().getOwnerId());
        universityRepository.deleteByOwnerId(userId);
        log.info("University with id {} has been deleted", userId);
    }

    /**
     * Creates a student profile for the university.
     *
     * @param studentRequest the student data to create the profile.
     * @param token          the authorization token.
     * @return the created student profile.
     */
    @Override
    public StudentRequest createStudentProfile(StudentRequest studentRequest, String token) {
        return studentClient.createStudent(studentRequest, token);
    }

    /**
     * Retrieves a list of students by the university's ID.
     *
     * @param id    the university ID.
     * @param page  the page number for pagination.
     * @param size  the size of the page for pagination.
     * @param token the authorization token.
     * @return a list of students for the specified university.
     */
    @Override
    @Cacheable(value = "feignCache", cacheManager = "cacheManager", key = "#id")
    public List<StudentDto> findStudentByUniversityId(Long id, int page, int size, String token) {
        return studentClient.findStudentByUniversityId(id, page, size, token);
    }

    /**
     * Deletes a student by their ID.
     *
     * @param id    the student ID.
     * @param token the authorization token.
     * @throws StudentNotFoundException if the student is not found.
     */
    @Override
    public void deleteStudentById(Long id, String token) {
        findStudentOrThrow(id, token);
        studentClient.deleteStudentById(id, token);
        log.info("Student with id {} has been deleted [from University-Service]", id);
    }

    /**
     * Updates a student's details by their ID.
     *
     * @param id              the student ID.
     * @param studentRequest  the updated student data.
     * @param token           the authorization token.
     * @throws StudentNotFoundException if the student is not found.
     */
    @Override
    public void updateStudentById(Long id, StudentRequest studentRequest, String token) {
        findStudentOrThrow(id, token);
        studentClient.updateStudentById(id, studentRequest, token);
        log.info("Updating student with id {}", id);
    }

    /**
     * Retrieves universities based on search filters.
     *
     * @param name     the name of the university to search for.
     * @param type     the type of the university to search for.
     * @param location the location of the university to search for.
     * @param pageable the pagination information.
     * @return a paginated list of universities matching the search criteria.
     */
    @Override
    public Page<UniversityDto> findByFilter(String name, UniversityType type, String location, Pageable pageable) {
        Page<University> universities = universityRepository.findAll(UniversitySpecification.withFilters(name, type, location), pageable);
        return universities.map(universityDtoMapper::toDto);
    }

    private University findUniversityOrThrow(Long id) throws UniversityNotFoundException {
        Optional<University> university = universityRepository.findById(id);
        if (university.isEmpty()) throw new UniversityNotFoundException(ExceptionMessages.UNIVERSITY_NOT_FOUND);
        return university.get();
    }

    private StudentRequest findStudentOrThrow(Long id, String token) throws StudentNotFoundException {
        Optional<StudentRequest> student = Optional.ofNullable(studentClient.findStudentById(id, token));
        if (student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        return student.get();
    }

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
