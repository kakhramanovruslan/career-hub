package com.project.authservice.client;

import com.project.authservice.model.university.UniversityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "api-gateway", url = "http://localhost:8080/university") //TODO url of api gateway
public interface UniversityClient {
    @PostMapping("/create")
    UniversityRequest createUniversityProfile(@RequestBody UniversityRequest universityRequest, @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteUniversityProfileByOwnerId(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
