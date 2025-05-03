package com.project.universityservice.service;

import com.project.universityservice.model.dto.StudentDto;
import com.project.universityservice.model.dto.StudentRequest;
import com.project.universityservice.model.dto.UniversityRequest;
import com.project.universityservice.model.dto.UniversityDto;
import com.project.universityservice.model.enums.UniversityType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service interface for handling operations related to universities and students.
 * This includes creating, updating, retrieving, and deleting universities and students.
 * The interface provides methods for managing universities and their associated students,
 * including access control and interacting with external student services.
 */
public interface UniversityService {

    /**
     * Finds a university by its owner ID.
     *
     * @param ownerId the ID of the university owner.
     * @return the {@link UniversityDto} of the found university.
     */
    UniversityDto findUniversityByOwnerId(Long ownerId);

    /**
     * Creates a new university.
     *
     * @param universityRequest the data for creating the university.
     * @return the {@link UniversityDto} of the created university.
     */
    UniversityDto createUniversity(UniversityRequest universityRequest);

    /**
     * Updates a university by its owner ID.
     *
     * @param ownerId           the ID of the university owner.
     * @param universityRequest the updated university data.
     * @param userId            the ID of the user attempting to update the university.
     */
    void updateUniversityByOwnerId(Long ownerId, UniversityRequest universityRequest, Long userId);

    /**
     * Deletes a university by its owner ID.
     *
     * @param userId the ID of the user requesting the deletion.
     */
    void deleteUniversityByOwnerId(Long userId);

    /**
     * Creates a student profile for the university.
     *
     * @param studentRequest the student data to create the profile.
     * @param token          the authorization token.
     * @return the created student profile.
     */
    StudentRequest createStudentProfile(StudentRequest studentRequest, String token);

    /**
     * Deletes a student by their ID.
     *
     * @param id    the student ID.
     * @param token the authorization token.
     */
    void deleteStudentById(Long id, String token);

    /**
     * Updates a student's details by their ID.
     *
     * @param id              the student ID.
     * @param studentRequest  the updated student data.
     * @param token           the authorization token.
     */
    void updateStudentById(Long id, StudentRequest studentRequest, String token);

    /**
     * Finds universities based on search filters.
     *
     * @param name     the name of the university to search for.
     * @param type     the type of the university to search for.
     * @param location the location of the university to search for.
     * @param pageable the pagination information.
     * @return a paginated list of universities matching the search criteria.
     */
    Page<UniversityDto> findByFilter(String name, UniversityType type, String location, Pageable pageable);

    /**
     * Retrieves students for a specific university based on the university ID.
     *
     * @param id    the university ID.
     * @param page  the page number for pagination.
     * @param size  the size of the page for pagination.
     * @param token the authorization token.
     * @return a list of students associated with the university.
     */
    List<StudentDto> findStudentByUniversityId(Long id, int page, int size, String token);
}
