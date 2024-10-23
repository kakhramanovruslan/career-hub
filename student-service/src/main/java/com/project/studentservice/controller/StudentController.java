package com.project.studentservice.controller;

import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Long id) throws StudentNotFoundException {
        return ResponseEntity.ok().body(studentService.findStudentById(id));
    }

//
//    @GetMapping("/mock")
//    public ResponseEntity<List<StudentDto>> getStudentsByFilter(){
//        return ResponseEntity.ok().body(new ArrayList<StudentDto>());
//    }
//
//    @PostMapping("")
//    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentRequest studentRequest){
//        return ResponseEntity.ok().body(new StudentDto());
//    }
//
//    @PutMapping("")
//    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentRequest studentRequest){
//        return ResponseEntity.ok().body(new StudentDto());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable  Long id){
//        return ResponseEntity.ok().body(null);
//    }

}
