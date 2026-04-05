package com.project.studentservice.repository;

import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import com.project.studentservice.model.types.DegreeEnum;
import com.project.studentservice.util.StudentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Student} entities.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for dynamic queries.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long>,
        JpaSpecificationExecutor<Student> {

    /**
     * Finds students by their associated university ID.
     *
     * @param id the university ID.
     * @param pageable pagination information.
     * @return a list of students associated with the given university ID.
     */
    List<Student> findStudentByUniversityId(Long id, Pageable pageable);

    /**
     * Deletes a student by the owner's ID.
     *
     * @param id the owner's ID of the student to delete.
     */
    void deleteByOwnerId(Long id);

    /**
     * Finds a student by the owner's ID.
     *
     * @param id the owner's ID.
     * @return an Optional containing the student, or empty if not found.
     */
    Optional<Student> findStudentByOwnerId(Long id);

    /**
     * Finds students by a list of owner IDs, with pagination support.
     *
     * @param studentOwnerIds a list of student owner IDs.
     * @param pageable pagination information.
     * @return a page of students with the specified owner IDs.
     */
    Page<Student> findByOwnerIdIn(List<Long> studentOwnerIds, Pageable pageable);

    List<Student> findByIdIn(List<Long> ids);
}
