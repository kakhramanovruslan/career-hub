package com.project.universityservice.controller;

import com.project.universityservice.client.StudentClient;
import com.project.universityservice.dto.StudentDto;
import com.project.universityservice.dto.StudentRequest;
import com.project.universityservice.dto.UniversityRequest;
import com.project.universityservice.dto.UniversityDto;
import com.project.universityservice.exception.StudentNotFoundException;
import com.project.universityservice.exception.UniversityNotFoundException;
import com.project.universityservice.model.University;
import com.project.universityservice.model.enums.UniversityType;
import com.project.universityservice.service.UniversityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/university")
@RestController
public class UniversityController {

    private final UniversityService universityService;

    @GetMapping("/search")
    public ResponseEntity<List<UniversityDto>> getUniversities(@RequestParam(required = false) String name,
                                                            @RequestParam(required = false) UniversityType type,
                                                            @RequestParam(required = false) String location){

        return ResponseEntity.ok().body(universityService.findByFilter(name, type, location));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id) throws UniversityNotFoundException {
        return ResponseEntity.ok().body(universityService.findUniversityById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UniversityDto> createUniversity(@RequestBody UniversityRequest universityRequest){
        return ResponseEntity.status(201).body(universityService.createUniversity(universityRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUniversityById(@PathVariable Long id, @RequestBody UniversityRequest universityRequest) throws UniversityNotFoundException {
        universityService.updateUniversityById(id, universityRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversityById(@PathVariable Long id) throws UniversityNotFoundException{
        universityService.deleteUniversityById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/students/createStudent")
    public ResponseEntity<StudentRequest> createStudentForUniversity(@RequestBody StudentRequest studentDto) {
        return ResponseEntity.status(201).body(universityService.createStudent(studentDto));
    }

    @GetMapping("/students/getStudentByUniversityId/{id}")
    public ResponseEntity<List<StudentDto>> getStudentByUniversityId(@PathVariable Long id){
        return ResponseEntity.ok().body(universityService.findStudentByUniversityId(id));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable  Long id) throws StudentNotFoundException {
        universityService.deleteStudentById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Void> updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest) throws StudentNotFoundException {
        universityService.updateStudentById(id, studentRequest);
        return ResponseEntity.ok().build();
    }
}
