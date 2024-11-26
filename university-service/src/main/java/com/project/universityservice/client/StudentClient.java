package com.project.universityservice.client;

import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service", url = "http://localhost:8080/student")
public interface StudentClient {
    @PostMapping("")
    StudentRequest createStudent(@RequestBody StudentRequest studentDto,  @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteStudentById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/{id}")
    StudentRequest findStudentById(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @GetMapping("/getByUniversityId/{id}")
    List<StudentDto> findStudentByUniversityId(@PathVariable Long id,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestHeader("Authorization") String token);

    @PutMapping("/{id}")
    void updateStudentById(@PathVariable Long id,
                           @RequestBody StudentRequest studentRequest,
                           @RequestHeader("Authorization") String token);
}
