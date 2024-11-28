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

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentDtoMapper studentDtoMapper;
    private final StudentRequestMapper studentRequestMapper;

    @Override
    public StudentDto findStudentById(Long id) throws StudentNotFoundException {
        Student student = findStudentOrThrow(id);
        log.info("Student with id {} has been sent to the client", id);
        return studentDtoMapper.toDto(student);
    }

    @Override
    public List<StudentDto> findStudentByUniversityId(Long id, Pageable pageable) {
        List<Student> students = studentRepository.findStudentByUniversityId(id, pageable);
        log.info("Student with university id {} has been sent to the client", id);
        return students.stream()
                .map(studentDtoMapper::toDto)
                .toList();
    }

    @Override
    public StudentDto addStudent(StudentRequest studentRequest) {
        Student student = studentRepository.save(studentRequestMapper.toEntity(studentRequest));
        StudentDto studentDto = studentDtoMapper.toDto(student);
        log.info("Adding student with id {} to the database", studentDto.getId());
        return studentDto;
    }

    @Override
    @Transactional
    public void deleteStudentByOwnerId(Long id, Long userId) throws StudentNotFoundException, AccessDeniedException {
        Optional<Student> student = studentRepository.findStudentByOwnerId(id);
        if(student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        isOwner(userId, student.get().getUniversityId());
        studentRepository.deleteByOwnerId(id);
        log.info("Student with id {} has been deleted", id);
    }

    @Override
    public void updateStudentById(Long id, StudentRequest studentRequest, Long userId) throws StudentNotFoundException, AccessDeniedException {
        Student student = findStudentOrThrow(id);
        isOwner(userId, student.getOwnerId());
        studentRepository.save(studentRequestMapper.updateStudentFromRequest(studentRequest, student));
        log.info("Updating student with id {}", id);
    }

    @Override
    public List<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degree, Integer currentYear, Double minGpa, Double maxGpa, Pageable pageable) {
        Page<Student> students = studentRepository.findAll(StudentSpecification.withFilters(firstName, lastName, degree, currentYear, minGpa, maxGpa), pageable);
        return students.stream()
                .map(studentDtoMapper::toDto)
                .toList();
    }


    private Student findStudentOrThrow(Long id) throws StudentNotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        return student.get();
    }

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
