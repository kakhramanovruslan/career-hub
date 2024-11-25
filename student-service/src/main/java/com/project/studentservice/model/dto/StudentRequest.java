package com.project.studentservice.model.dto;

import com.project.studentservice.model.types.DegreeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

    private String firstName;

    private String lastName;

    private String email;

    private Long ownerId;

    private Long universityId;

    private BigDecimal gpa;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private DegreeEnum degree;

    private Integer currentYear;

    private Integer yearOfEnrollment;

}
