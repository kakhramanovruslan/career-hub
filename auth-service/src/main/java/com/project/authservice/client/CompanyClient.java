package com.project.authservice.client;

import com.project.authservice.model.company.CompanyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * Feign client interface for interacting with the API Gateway to manage company profiles.
 */
@FeignClient(name = "api-gatewayy", url = "http://localhost:8080/company") //TODO url of api gateway
public interface CompanyClient {

    /**
     * Creates a new company profile.
     *
     * @param companyRequest the company profile data to create
     * @param token the authorization token for the request
     * @return the created company profile
     */
    @PostMapping("/create")
    CompanyRequest createCompanyProfile(@RequestBody CompanyRequest companyRequest, @RequestHeader("Authorization") String token);

    /**
     * Deletes a company profile by the owner's ID.
     *
     * @param id the ID of the company to delete
     * @param token the authorization token for the request
     */
    @DeleteMapping("/{id}")
    void deleteCompanyProfileByOwnerId(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
