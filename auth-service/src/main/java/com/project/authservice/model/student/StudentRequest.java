package com.project.authservice.model.student;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {
    private Long ownerId;

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
}
