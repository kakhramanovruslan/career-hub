package com.project.studentservice.repository;

import com.project.studentservice.model.entity.CompanySearchQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchQueryRepository extends JpaRepository<CompanySearchQuery, Long> {

    long countByCompanyId(Long companyId);

    List<CompanySearchQuery> findTop10ByCompanyIdOrderByCreatedAtDesc(Long companyId);

    List<CompanySearchQuery> findByCompanyIdOrderByCreatedAtAsc(Long companyId, Pageable pageable);
}