package com.project.studentservice.service.impl;

import com.project.studentservice.exception.ExceptionMessages;
import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.mapper.StudentDtoMapper;
import com.project.studentservice.mapper.StudentRequestMapper;
import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import com.project.studentservice.repository.StudentRepository;
import com.project.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentDtoMapper studentDtoMapper;
    private final StudentRequestMapper studentRequestMapper;

    @Override
    public StudentDto findStudentById(Long id) throws StudentNotFoundException, SQLException {
        Student student = findStudentOrThrow(id);
        log.info("Student with id {} has been sent to the client", id);
        return studentDtoMapper.toDto(student);
    }

    @Override
    public StudentDto addStudent(StudentRequest studentRequest) throws SQLException {
        Student student = studentRepository.save(studentRequestMapper.toEntity(studentRequest));
        StudentDto studentDto = studentDtoMapper.toDto(student);
        log.info("Adding student with id {} to the database", studentDto.getId());
        return studentDto;
    }

    @Override
    public void deleteStudentById(Long id) throws StudentNotFoundException, SQLException {
        findStudentOrThrow(id);
        studentRepository.deleteById(id);
        log.info("Student with id {} has been deleted", id);
    }

    @Override
    public void updateStudentById(Long id, StudentRequest studentRequest) throws StudentNotFoundException, SQLException {
        Student student = findStudentOrThrow(id);
        studentRepository.save(studentRequestMapper.updateStudentFromRequest(studentRequest, student));
        log.info("Updating student with id {}", id);
    }


    private Student findStudentOrThrow(Long id) throws StudentNotFoundException {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) throw new StudentNotFoundException(ExceptionMessages.STUDENT_NOT_FOUND);
        return student.get();
    }
}
