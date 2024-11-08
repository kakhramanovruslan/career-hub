package com.project.universityservice.client;

import com.project.universityservice.dto.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "student-service", url = "http://localhost:8081/student")
public interface StudentClient {
    @PostMapping("")
    StudentRequest createStudent(@RequestBody StudentRequest studentDto);

    @DeleteMapping("/{id}")
    void deleteStudentById(@PathVariable Long id);

    @GetMapping("/{id}")
    StudentRequest findStudentById(@PathVariable Long id);

    @PutMapping("/{id}")
    void updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest);
}
