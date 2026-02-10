package com.project.studentservice.service.impl;

import com.project.studentservice.exception.AccessDeniedException;
import com.project.studentservice.model.types.DegreeEnum;
import com.project.studentservice.util.ExceptionMessages;
import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.mapper.StudentDtoMapper;
import com.project.studentservice.mapper.StudentRequestMapper;
import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import com.project.studentservice.repository.StudentRepository;
import com.project.studentservice.service.StudentService;
import com.project.studentservice.util.StudentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing students.
 * Provides methods to add, update, delete, and fetch student data.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentDtoMapper studentDtoMapper;
    private final StudentRequestMapper studentRequestMapper;

    /**
     * Finds a student by the owner's ID.
     *
     * @param ownerId the ID of the student owner.
     * @return the student data transfer object (DTO).
     * @throws StudentNotFoundException if the student with the given ownerId is not found.
     */
    @Override
    public StudentDto findStudentByOwnerId(Long ownerId) throws StudentNotFoundException {
        Optional<Student> student = studentRepository.findStudentByOwnerId(ownerId);
        if (student.isPresent()) {
            return studentDtoMapper.toDto(student.get());
        } else {
            throw new StudentNotFoundException("University not found for ownerId: " + ownerId);
        }
    }

    /**
     * Finds students by university ID.
     *
     * @param id the university ID.
     * @param pageable pagination information.
     * @return a list of student DTOs.
     */
    @Override
    public List<StudentDto> findStudentByUniversityId(Long id, Pageable pageable) {
        List<Student> students = studentRepository.findStudentByUniversityId(id, pageable);
        log.info("Student with university id {} has been sent to the client", id);
        return students.stream()
                .map(studentDtoMapper::toDto)
                .toList();
    }

    /**
     * Adds a new student.
     *
     * @param studentRequest the student data to add.
     * @return the added student DTO.
     */
    @Override
    public StudentDto addStudent(StudentRequest studentRequest) {
        Student student = studentRepository.save(studentRequestMapper.toEntity(studentRequest));
        StudentDto studentDto = studentDtoMapper.toDto(student);
        log.info("Adding student with id {} to the database", studentDto.getId());
        return studentDto;
    }

    /**
     * Deletes a student by their owner ID.
     *
     * @param id the student owner's ID.
     * @param userId the ID of the user requesting the deletion.
     * @throws StudentNotFoundException if the student is not found.
     * @throws AccessDeniedException if the user does not have permission to delete the student.
     */
    @Override
    @Transactional
    public void deleteStudentByOwnerId(Long id, Long userId) throws StudentNotFoundException, AccessDeniedException {
        Optional<Student> student = studentRepository.findStudentByOwnerId(id);
        if (student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        isOwner(userId, student.get().getUniversityId());
        studentRepository.deleteByOwnerId(id);
        log.info("Student with id {} has been deleted", id);
    }

    /**
     * Updates a student's profile.
     *
     * @param ownerId the student owner's ID.
     * @param studentRequest the student data to update.
     * @param userId the ID of the user requesting the update.
     * @throws StudentNotFoundException if the student is not found.
     * @throws AccessDeniedException if the user does not have permission to update the student.
     */
    @Override
    public void updateStudentProfileByOwnerId(Long ownerId, StudentRequest studentRequest, Long userId) throws StudentNotFoundException, AccessDeniedException {
        Optional<Student> student = studentRepository.findStudentByOwnerId(ownerId);
        if (student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        isOwner(userId, ownerId);
        studentRepository.save(studentRequestMapper.updateStudentFromRequest(studentRequest, student.get()));
        log.info("Updating student with id {}", student.get().getId());
    }

    /**
     * Finds students by filters (e.g., first name, last name, degree, etc.).
     *
     * @param firstName the student's first name.
     * @param lastName the student's last name.
     * @param degree the student's degree.
     * @param currentYear the student's current year.
     * @param universityId the student's university ID.
     * @param minGpa the minimum GPA.
     * @param maxGpa the maximum GPA.
     * @param pageable pagination information.
     * @return a page of student DTOs matching the filters.
     */
    @Override
    public Page<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degree, Integer currentYear, Long universityId, Double minGpa, Double maxGpa, Pageable pageable) {
        Page<Student> students = studentRepository.findAll(StudentSpecification.withFilters(firstName, lastName, degree, currentYear, universityId, minGpa, maxGpa), pageable);
        return students.map(studentDtoMapper::toDto);
    }

    /**
     * Finds students by a batch of owner IDs.
     *
     * @param studentOwnerIds a list of student owner IDs.
     * @param pageable pagination information.
     * @return a page of student DTOs matching the owner IDs.
     */
    @Override
    public Page<StudentDto> findByStudentsBatch(List<Long> studentOwnerIds, Pageable pageable) {
        Page<Student> students = studentRepository.findByOwnerIdIn(studentOwnerIds, pageable);
        return students.map(studentDtoMapper::toDto);
    }

    /**
     * Helper method to find a student by ID or throw an exception if not found.
     *
     * @param id the student's ID.
     * @return the student.
     * @throws StudentNotFoundException if the student is not found.
     */
    private Student findStudentOrThrow(Long id) throws StudentNotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        return student.get();
    }

    /**
     * Helper method to check if the user is the owner of the student.
     *
     * @param id the user's ID.
     * @param ownerId the student's owner ID.
     * @throws AccessDeniedException if the user is not the owner.
     */
    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
