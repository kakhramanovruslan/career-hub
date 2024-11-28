package com.project.companyservice.model.entity;

import com.project.companyservice.model.enums.CompanyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ownerId;

    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    private String email;

    private String location;

    private String contactPhone;

    private String industry;

    private String website;

    private Integer establishedYear;

    //todo list of savedStudents

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
