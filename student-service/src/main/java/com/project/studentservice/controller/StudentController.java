package com.project.studentservice.controller;

import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) throws StudentNotFoundException, SQLException {
        return ResponseEntity.ok().body(studentService.findStudentById(id));
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

//
//    @GetMapping("/mock")
//    public ResponseEntity<List<StudentDto>> getStudentsByFilter(){
//        return ResponseEntity.ok().body(new ArrayList<StudentDto>());
//    }
//

}
