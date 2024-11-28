package com.project.authservice.client;

import com.project.authservice.model.company.CompanyRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "company-service", url = "http://localhost:8080/company") //TODO url of api gateway
public interface CompanyClient {
    @PostMapping("/create")
    CompanyRequest createCompany(@RequestBody CompanyRequest companyRequest);

    @DeleteMapping("/{id}")
    void deleteCompanyByOwnerId(@PathVariable Long id);
}
