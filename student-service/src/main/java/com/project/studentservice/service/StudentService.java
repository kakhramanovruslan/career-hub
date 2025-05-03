package com.project.studentservice.service;

import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.types.DegreeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;

/**
 * Service interface for managing student-related operations.
 * Provides methods for adding, updating, deleting, and fetching students.
 */
public interface StudentService {

    /**
     * Finds a student by the owner's ID.
     *
     * @param id the ID of the student owner.
     * @return the student data transfer object (DTO).
     * @throws SQLException if there is a database error.
     */
    StudentDto findStudentByOwnerId(Long id) throws SQLException;

    /**
     * Adds a new student.
     *
     * @param studentRequest the student data to add.
     * @return the added student DTO.
     * @throws SQLException if there is a database error.
     */
    StudentDto addStudent(StudentRequest studentRequest) throws SQLException;

    /**
     * Deletes a student by their owner's ID.
     *
     * @param id the student's owner's ID.
     * @param userId the ID of the user requesting the deletion.
     * @throws SQLException if there is a database error.
     */
    void deleteStudentByOwnerId(Long id, Long userId) throws SQLException;

    /**
     * Finds students by their university ID.
     *
     * @param id the university ID.
     * @param pageable pagination information.
     * @return a list of student DTOs.
     */
    List<StudentDto> findStudentByUniversityId(Long id, Pageable pageable);

    /**
     * Updates a student's profile by their owner's ID.
     *
     * @param id the student owner's ID.
     * @param studentRequest the data to update the student with.
     * @param userId the ID of the user requesting the update.
     * @throws SQLException if there is a database error.
     */
    void updateStudentProfileByOwnerId(Long id, StudentRequest studentRequest, Long userId) throws SQLException;

    /**
     * Finds students based on various filters (e.g., name, degree, GPA).
     *
     * @param firstName the student's first name.
     * @param lastName the student's last name.
     * @param degreeEnum the student's degree.
     * @param currentYear the student's current year.
     * @param universityId the university ID.
     * @param minGpa the minimum GPA.
     * @param maxGpa the maximum GPA.
     * @param pageable pagination information.
     * @return a page of student DTOs matching the filters.
     */
    Page<StudentDto> findByFilter(String firstName, String lastName, DegreeEnum degreeEnum, Integer currentYear, Long universityId, Double minGpa, Double maxGpa, Pageable pageable);

    /**
     * Finds students by a batch of owner IDs.
     *
     * @param studentOwnerIds a list of student owner IDs.
     * @param pageable pagination information.
     * @return a page of student DTOs matching the owner IDs.
     */
    Page<StudentDto> findByStudentsBatch(List<Long> studentOwnerIds, Pageable pageable);
}
