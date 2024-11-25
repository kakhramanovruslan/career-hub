package com.project.universityservice.controller;

import com.project.universityservice.exception.AccessDeniedException;
import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.exception.StudentNotFoundException;
import com.project.universityservice.exception.UniversityNotFoundException;
import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.enums.UniversityType;
import com.project.universityservice.model.enums.UserRole;
import com.project.universityservice.service.UniversityService;
import com.project.universityservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                                                               @RequestParam(required = false) String location,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(universityService.findByFilter(name, type, location, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniversityDto> getUniversityById(@PathVariable Long id) throws UniversityNotFoundException {
        return ResponseEntity.ok().body(universityService.findUniversityById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<UniversityDto> createUniversity(@RequestBody UniversityRequest universityRequest,
                                                          @RequestHeader("X-User-Role") UserRole role,
                                                          @RequestHeader("X-User-Id") Long userId){
        hasRole(role, List.of(UserRole.UNIVERSITY));
        universityRequest.setOwnerId(userId);
        return ResponseEntity.status(201).body(universityService.createUniversity(universityRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateUniversityById(@PathVariable Long id,
                                                     @RequestBody UniversityRequest universityRequest,
                                                     @RequestHeader("X-User-Role") UserRole role,
                                                     @RequestHeader("X-User-Id") Long userId)
            throws UniversityNotFoundException {
        hasRole(role, List.of(UserRole.UNIVERSITY));
        universityService.updateUniversityById(id, universityRequest, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUniversityById(@PathVariable Long id,
                                                     @RequestHeader("X-User-Role") UserRole role,
                                                     @RequestHeader("X-User-Id") Long userId)
            throws UniversityNotFoundException{
        hasRole(role, List.of(UserRole.UNIVERSITY));
        universityService.deleteUniversityById(id, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/students/createStudent")
    public ResponseEntity<StudentRequest> createStudentForUniversity(@RequestBody StudentRequest studentDto) {
        return ResponseEntity.status(201).body(universityService.createStudent(studentDto));
    }

    @GetMapping("/students/getStudentByUniversityId/{id}")
    public ResponseEntity<List<StudentDto>> getStudentByUniversityId(@PathVariable Long id,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok().body(universityService.findStudentByUniversityId(id, page, size));
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

    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
