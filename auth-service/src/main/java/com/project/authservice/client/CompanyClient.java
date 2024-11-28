package com.project.authservice.client;

import com.project.authservice.model.company.CompanyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "api-gatewayy", url = "http://localhost:8080/company") //TODO url of api gateway
public interface CompanyClient {
    @PostMapping("/create")
    CompanyRequest createCompanyProfile(@RequestBody CompanyRequest companyRequest, @RequestHeader("Authorization") String token);

    @DeleteMapping("/{id}")
    void deleteCompanyProfileByOwnerId(@PathVariable Long id, @RequestHeader("Authorization") String token);
}
