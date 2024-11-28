package com.project.authservice.model.company;

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

    private String location;

    private String contactPhone;

    private String industry;

    private String website;

    private Integer establishedYear;
}
