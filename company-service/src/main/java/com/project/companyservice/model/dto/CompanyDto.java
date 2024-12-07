package com.project.companyservice.model.dto;

import com.project.companyservice.model.enums.CompanyType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private Long id;

    private Long ownerId;

    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    private String email;

    private String location;

    private String aboutUs;

    private String contactPhone;

    private String industry;

    private String website;

    private Integer establishedYear;
}
