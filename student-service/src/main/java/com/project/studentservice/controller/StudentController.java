package com.project.studentservice.controller;

import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.types.DegreeEnum;
import com.project.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/search")
    public ResponseEntity<List<StudentDto>> getStudents(@RequestParam(required = false) String firstName,
                                                       @RequestParam(required = false) String lastName,
                                                       @RequestParam(required = false) DegreeEnum degree,
                                                       @RequestParam(required = false) Integer currentYear,
                                                       @RequestParam(required = false) Double minGpa,
                                                       @RequestParam(required = false) Double maxGpa,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findByFilter(firstName, lastName, degree, currentYear, minGpa, maxGpa, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) throws StudentNotFoundException, SQLException {
        return ResponseEntity.ok().body(studentService.findStudentById(id));
    }

    @GetMapping("/getByUniversityId/{id}")
    public ResponseEntity<List<StudentDto>> getByUniversityId(@PathVariable Long id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) throws StudentNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findStudentByUniversityId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentRequest studentRequest) throws SQLException {
        return ResponseEntity.status(201).body(studentService.addStudent(studentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable  Long id) throws StudentNotFoundException, SQLException {
        studentService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest) throws StudentNotFoundException, SQLException {
        studentService.updateStudentById(id, studentRequest);
        return ResponseEntity.ok().build();
    }
}
