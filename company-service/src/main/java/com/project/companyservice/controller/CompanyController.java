package com.project.companyservice.controller;

import com.project.companyservice.dto.CompanyDto;
import com.project.companyservice.dto.CompanyRequest;
import com.project.companyservice.exception.CompanyNotFoundException;
import com.project.companyservice.model.Company;
import com.project.companyservice.model.enums.CompanyType;
import com.project.companyservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/company")
@RestController
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/search")
    public ResponseEntity<List<CompanyDto>> getCompanies(@RequestParam(required = false) String name,
                                                         @RequestParam(required = false) CompanyType type,
                                                         @RequestParam(required = false) String location,
                                                         @RequestParam(required = false) String industry,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(companyService.findByFilter(name, type, location, industry, pageable));
    }

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
