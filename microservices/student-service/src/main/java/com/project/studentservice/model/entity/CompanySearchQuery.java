package com.project.studentservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "company_search_queries",
        indexes = {@Index(name = "idx_company_search_queries_company_created_at", columnList = "company_id, created_at")}
)
public class CompanySearchQuery {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "query_text", nullable = false, length = 1000)
    private String queryText;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}