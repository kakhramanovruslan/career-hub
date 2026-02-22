package com.project.universityservice.client;

import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Feign client interface for interacting with the student service.
 * Provides methods to create, retrieve, update, and delete student information
 * through HTTP requests to the student service API.
 */
@FeignClient(name = "student-service", url = "http://localhost:8080/student")
public interface StudentClient {

    /**
     * Creates a new student.
     *
     * @param studentDto the student data to be created.
     * @param token the authorization token.
     * @return the created student data.
     */
    @PostMapping("")
    StudentRequest createStudent(@RequestBody StudentRequest studentDto, @RequestHeader("Authorization") String token);

    /**
     * Deletes a student by their ID.
     *
     * @param id the ID of the student to delete.
     * @param token the authorization token.
     */
    @DeleteMapping("/{id}")
    void deleteStudentById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    /**
     * Retrieves a student by their ID.
     *
     * @param id the ID of the student to retrieve.
     * @param token the authorization token.
     * @return the student data.
     */
    @GetMapping("/{id}")
    StudentRequest findStudentById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    /**
     * Retrieves a list of students by university ID, with pagination.
     *
     * @param id the university ID.
     * @param page the page number (default is 0).
     * @param size the page size (default is 10).
     * @param token the authorization token.
     * @return a list of students associated with the specified university ID.
     */
    @GetMapping("/getByUniversityId/{id}")
    List<StudentDto> findStudentByUniversityId(@PathVariable Long id,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestHeader("Authorization") String token);

    /**
     * Updates the details of an existing student by their ID.
     *
     * @param id the ID of the student to update.
     * @param studentRequest the new student data to update with.
     * @param token the authorization token.
     */
    @PutMapping("/{id}")
    void updateStudentById(@PathVariable Long id,
                           @RequestBody StudentRequest studentRequest,
                           @RequestHeader("Authorization") String token);
}
