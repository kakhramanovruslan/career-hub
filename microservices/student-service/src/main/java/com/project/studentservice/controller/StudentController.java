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

/**
 * Controller class for managing student profiles.
 * Provides endpoints for searching, retrieving, creating, updating, and deleting student data.
 */
@RequiredArgsConstructor
@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    /**
     * Endpoint to search students based on various filters.
     *
     * @param firstName    the first name of the student (optional)
     * @param lastName     the last name of the student (optional)
     * @param degree       the degree of the student (optional)
     * @param currentYear  the current year of the student (optional)
     * @param universityId the university ID the student belongs to (optional)
     * @param minGpa       the minimum GPA for filtering students (optional)
     * @param maxGpa       the maximum GPA for filtering students (optional)
     * @param page         the page number for pagination (default: 0)
     * @param size         the size of each page for pagination (default: 10)
     * @return a page of students that match the provided filters
     */
    @GetMapping("/search")
    public ResponseEntity<Page<StudentDto>> getStudents(@RequestParam(required = false) String firstName,
                                                        @RequestParam(required = false) String lastName,
                                                        @RequestParam(required = false) DegreeEnum degree,
                                                        @RequestParam(required = false) Integer currentYear,
                                                        @RequestParam(required = false) Long universityId,
                                                        @RequestParam(required = false) Double minGpa,
                                                        @RequestParam(required = false) Double maxGpa,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findByFilter(firstName, lastName, degree, currentYear, universityId, minGpa, maxGpa, pageable));
    }

    /**
     * Endpoint to retrieve students based on a batch of student owner IDs.
     *
     * @param studentOwnerIds the list of student owner IDs
     * @param page            the page number for pagination (default: 0)
     * @param size            the size of each page for pagination (default: 10)
     * @return a page of students corresponding to the provided batch of owner IDs
     */
    @GetMapping("/getBatch")
    public ResponseEntity<Page<StudentDto>> getStudentsWithBatch(@RequestBody List<Long> studentOwnerIds,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findByStudentsBatch(studentOwnerIds, pageable));
    }

    /**
     * Endpoint to retrieve a student by their owner ID.
     *
     * @param ownerId the owner ID of the student
     * @return the student details
     * @throws StudentNotFoundException if the student is not found
     * @throws SQLException if there is an error querying the database
     */
    @GetMapping("/{ownerId}")
    public ResponseEntity<StudentDto> getStudentByOwnerId(@PathVariable Long ownerId) throws StudentNotFoundException, SQLException {
        return ResponseEntity.ok().body(studentService.findStudentByOwnerId(ownerId));
    }

    /**
     * Endpoint to retrieve students by their university ID.
     *
     * @param id     the university ID
     * @param page   the page number for pagination (default: 0)
     * @param size   the size of each page for pagination (default: 10)
     * @return a list of students enrolled in the specified university
     * @throws StudentNotFoundException if no students are found for the university ID
     */
    @GetMapping("/getByUniversityId/{id}")
    public ResponseEntity<List<StudentDto>> getByUniversityId(@PathVariable Long id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) throws StudentNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(studentService.findStudentByUniversityId(id, pageable));
    }

    /**
     * Endpoint to create a new student profile.
     *
     * @param studentRequest the request body containing student details
     * @param role           the role of the current user (e.g., UNIVERSITY)
     * @return the created student profile
     * @throws SQLException if there is an error querying the database
     */
    @PostMapping
    public ResponseEntity<StudentDto> createStudentProfile(@RequestBody StudentRequest studentRequest,
                                                           @RequestHeader("X-User-Role") UserRole role)
            throws SQLException {
        hasRole(role, List.of(UserRole.UNIVERSITY));
        return ResponseEntity.status(201).body(studentService.addStudent(studentRequest));
    }

    /**
     * Endpoint to delete a student profile by their owner ID.
     *
     * @param id      the owner ID of the student to be deleted
     * @param role    the role of the current user
     * @param userId  the ID of the user making the request
     * @return a response indicating the deletion was successful
     * @throws StudentNotFoundException if the student is not found
     * @throws SQLException if there is an error querying the database
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentProfileByOwnerId(@PathVariable Long id,
                                                              @RequestHeader("X-User-Role") UserRole role,
                                                              @RequestHeader("X-User-Id") Long userId)
            throws StudentNotFoundException, SQLException {
        hasRole(role, List.of(UserRole.UNIVERSITY));
        studentService.deleteStudentByOwnerId(id, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint to update a student profile by their owner ID.
     *
     * @param ownerId         the owner ID of the student
     * @param studentRequest  the request body containing updated student details
     * @param role            the role of the current user
     * @param userId          the ID of the user making the request
     * @return a response indicating the update was successful
     * @throws StudentNotFoundException if the student is not found
     * @throws SQLException if there is an error querying the database
     */
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

    /**
     * Helper method to check if the current user has the required role.
     *
     * @param currentRole  the current role of the user
     * @param requiredRoles the list of required roles
     * @throws AccessDeniedException if the user does not have the required role
     */
    private void hasRole(UserRole currentRole, List<UserRole> requiredRoles) throws AccessDeniedException {
        if (!requiredRoles.contains(currentRole)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
