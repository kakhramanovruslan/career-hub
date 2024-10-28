package com.project.companyservice.controller;

import com.project.companyservice.dto.CompanyDto;
import com.project.companyservice.dto.CompanyRequest;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id) throws CompanyNotFoundException {
        return ResponseEntity.ok().body(companyService.findCompanyById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<CompanyDto> createCompany(@RequestBody CompanyRequest companyRequest){
        return ResponseEntity.status(201).body(companyService.createCompany(companyRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateCompanyById(@PathVariable Long id, @RequestBody CompanyRequest companyRequest) throws CompanyNotFoundException {
        companyService.updateCompanyById(id, companyRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable Long id) throws CompanyNotFoundException{
        companyService.deleteCompanyById(id);
        return ResponseEntity.ok().build();
    }
}
