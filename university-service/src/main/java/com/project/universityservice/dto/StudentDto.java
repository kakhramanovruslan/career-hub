package com.project.universityservice.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long universityId;

    private BigDecimal gpa;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private DegreeEnum degree;

    private Integer currentYear;

    private Integer yearOfEnrollment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
