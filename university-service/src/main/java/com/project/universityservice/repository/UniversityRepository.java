package com.project.universityservice.repository;

import com.project.universityservice.model.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UniversityRepository extends JpaRepository<University, Long>,
                                              JpaSpecificationExecutor<University> {
    void deleteByOwnerId(Long id);
    Optional<University> findUniversityByOwnerId(Long id);
}
