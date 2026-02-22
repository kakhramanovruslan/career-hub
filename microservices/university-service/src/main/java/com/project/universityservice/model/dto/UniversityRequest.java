package com.project.universityservice.model.dto;

import com.project.universityservice.model.enums.UniversityType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityRequest {

    private Long ownerId; // К AuthService кто создал универ

    private String name;

    @Enumerated(EnumType.STRING)
    private UniversityType type;

    private String email;

    private String contactPhone;

    private String location;

    private Integer establishedYear;

    private String website;

    private String aboutUs;
}
