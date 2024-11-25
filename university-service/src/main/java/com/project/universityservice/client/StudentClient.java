package com.project.universityservice.client;

import com.project.universityservice.dto.StudentDto;
import com.project.universityservice.dto.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "student-service", url = "http://localhost:8081/student")
public interface StudentClient {
    @PostMapping("")
    StudentRequest createStudent(@RequestBody StudentRequest studentDto);

    @DeleteMapping("/{id}")
    void deleteStudentById(@PathVariable Long id);

    @GetMapping("/{id}")
    StudentRequest findStudentById(@PathVariable Long id);

    @GetMapping("/getByUniversityId/{id}")
    List<StudentDto> findStudentByUniversityId(@PathVariable Long id,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size);

    @PutMapping("/{id}")
    void updateStudentById(@PathVariable Long id, @RequestBody StudentRequest studentRequest);
}
