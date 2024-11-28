package com.project.authservice.client;

import com.project.authservice.model.student.StudentRequest;
import com.project.authservice.model.university.UniversityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "university-service", url = "http://localhost:8080/university") //TODO url of api gateway
public interface UniversityClient {
    @PostMapping("/create")
    UniversityRequest createUniversity(@RequestBody UniversityRequest universityRequest);

    @DeleteMapping("/{id}")
    void deleteUniversityProfileByOwnerId(@PathVariable Long id);
}
