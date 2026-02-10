package com.project.authservice.client;

import com.project.authservice.model.student.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client interface for interacting with the Student Service to manage student profiles.
 */
@FeignClient(name = "student-service", url = "http://localhost:8080/student") //TODO url of api gateway
public interface StudentClient {

    /**
     * Creates a new student profile.
     *
     * @param studentDto the student profile data to create
     * @param token the authorization token for the request
     * @return the created student profile
     */
    @PostMapping("")
    StudentRequest createStudentProfile(@RequestBody StudentRequest studentDto, @RequestHeader("Authorization") String token);

    /**
     * Deletes a student profile by the user's ID.
     *
     * @param userId the ID of the student to delete
     * @param token the authorization token for the request
     */
    @DeleteMapping("/{userId}")
    void deleteStudentProfileByOwnerId(@PathVariable Long userId, @RequestHeader("Authorization") String token);
}
