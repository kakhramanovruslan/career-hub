package com.project.authservice.client;

import com.project.authservice.model.university.UniversityRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client interface for interacting with the University Service to manage university profiles.
 */
@FeignClient(name = "api-gateway", url = "http://localhost:8080/university") //TODO url of api gateway
public interface UniversityClient {

    /**
     * Creates a new university profile.
     *
     * @param universityRequest the university profile data to create
     * @param token the authorization token for the request
     * @return the created university profile
     */
    @PostMapping("/create")
    UniversityRequest createUniversityProfile(@RequestBody UniversityRequest universityRequest, @RequestHeader("Authorization") String token);

    /**
     * Deletes a university profile by the owner's ID.
     *
     * @param id the ID of the university to delete
     * @param token the authorization token for the request
     */
    @DeleteMapping("/{id}")
    void deleteUniversityProfileByOwnerId(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
