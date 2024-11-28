package com.project.companyservice.repository;

import com.project.companyservice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>,
                                           JpaSpecificationExecutor<Company> {
    void deleteByOwnerId(Long id);
}
