package com.project.universityservice.model.dto;

import com.project.universityservice.model.enums.UniversityType;
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
public class UniversityDto {
    private Long id;

    private Long ownerId;

    private String name;

    @Enumerated(EnumType.STRING)
    private UniversityType type;

    private String email;

    private String contactPhone;

    private String location;

    private Integer establishedYear;

    private String website;
}
