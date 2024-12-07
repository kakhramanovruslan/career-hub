package com.project.studentservice.model.entity;

import com.project.studentservice.model.types.DegreeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long universityId;

    private Long ownerId;

    private BigDecimal gpa;

    private String aboutUs;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private DegreeEnum degree;

    private Integer currentYear;

    private Integer yearOfEnrollment;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
