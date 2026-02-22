package com.project.universityservice.repository;

import com.project.universityservice.model.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repository interface for {@link University} entity.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor}
 * for advanced querying capabilities with specifications.
 */
public interface UniversityRepository extends JpaRepository<University, Long>,
        JpaSpecificationExecutor<University> {

    /**
     * Deletes a {@link University} by its owner ID.
     *
     * @param id the owner ID of the {@link University} to delete.
     */
    void deleteByOwnerId(Long id);

    /**
     * Finds a {@link University} by its owner ID.
     *
     * @param id the owner ID of the {@link University} to find.
     * @return an {@link Optional} containing the {@link University} if found, otherwise empty.
     */
    Optional<University> findUniversityByOwnerId(Long id);
}
