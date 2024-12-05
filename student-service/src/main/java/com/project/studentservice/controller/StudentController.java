package com.project.studentservice.controller;

import com.project.studentservice.exception.AccessDeniedException;
import com.project.studentservice.exception.StudentNotFoundException;
import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.types.DegreeEnum;
import com.project.studentservice.model.types.UserRole;
import com.project.studentservice.service.StudentService;
import com.project.studentservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Page<StudentDto>> getStudents(@RequestParam(required = false) String firstName,
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

    @GetMapping("/getBatch")
    public ResponseEntity<Page<StudentDto>> getStudentsWithBatch(@RequestBody List<Long> studentOwnerIds,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findByStudentsBatch(studentOwnerIds, pageable));
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<StudentDto> getStudentByOwnerId(@PathVariable Long ownerId) throws StudentNotFoundException, SQLException {
        return ResponseEntity.ok().body(studentService.findStudentByOwnerId(ownerId));
    }

    @GetMapping("/getByUniversityId/{id}")
    public ResponseEntity<List<StudentDto>> getByUniversityId(@PathVariable Long id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) throws StudentNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findStudentByUniversityId(id, pageable));
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudentProfile(@RequestBody StudentRequest studentRequest,
                                                           @RequestHeader("X-User-Role") UserRole role)
            throws SQLException {
        System.out.println(studentRequest.toString());
        hasRole(role, List.of(UserRole.UNIVERSITY));
        return ResponseEntity.status(201).body(studentService.addStudent(studentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentProfileByOwnerId(@PathVariable Long id,
                                                  @RequestHeader("X-User-Role") UserRole role,
                                                  @RequestHeader("X-User-Id") Long userId)
            throws StudentNotFoundException, SQLException {
        hasRole(role, List.of(UserRole.UNIVERSITY));
        studentService.deleteStudentByOwnerId(id, userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<Void> updateStudentProfileByOwnerId(@PathVariable Long ownerId,
                                                  @RequestBody StudentRequest studentRequest,
                                                  @RequestHeader("X-User-Role") UserRole role,
                                                  @RequestHeader("X-User-Id") Long userId)
            throws StudentNotFoundException, SQLException {
        hasRole(role, List.of(UserRole.STUDENT));
        studentService.updateStudentProfileByOwnerId(ownerId, studentRequest, userId);
        return ResponseEntity.ok().build();
    }

    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
