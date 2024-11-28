package com.project.companyservice.repository;

import com.project.companyservice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>,
                                           JpaSpecificationExecutor<Company> {
    void deleteByOwnerId(Long id);
    Optional<Company> findCompanyByOwnerId(Long id);
}
