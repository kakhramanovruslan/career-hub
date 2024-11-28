package com.project.authservice.client;

import com.project.authservice.model.student.StudentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "student-service", url = "http://localhost:8080/student") //TODO url of api gateway
public interface StudentClient {
    @PostMapping("")
    StudentRequest createStudentProfile(@RequestBody StudentRequest studentDto, @RequestHeader("Authorization") String token);

    @DeleteMapping("/{userId}")
    void deleteStudentProfileByOwnerId(@PathVariable Long userId, @RequestHeader("Authorization") String token);
}
