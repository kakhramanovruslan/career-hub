package com.project.companyservice.repository;

import com.project.companyservice.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for managing {@link Company} entities.
 */
@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>,
        JpaSpecificationExecutor<Company> {

    /**
     * Deletes a {@link Company} by its owner ID.
     */
    void deleteByOwnerId(Long id);

    /**
     * Finds a {@link Company} by its owner ID.
     */
    Optional<Company> findCompanyByOwnerId(Long id);
}
