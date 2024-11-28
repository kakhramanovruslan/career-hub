package com.project.companyservice.model.dto;

import com.project.companyservice.model.enums.CompanyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequest {
    private String name;

    private Long ownerId;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    private String email;

    private String location;

    private String contactPhone;

    private String industry;

    private String website;

    private Integer establishedYear;
}
