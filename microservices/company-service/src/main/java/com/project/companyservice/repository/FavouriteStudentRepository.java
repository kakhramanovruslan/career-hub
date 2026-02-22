package com.project.companyservice.repository;

import com.project.companyservice.model.entity.FavouriteStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing {@link FavouriteStudent} entities.
 */
public interface FavouriteStudentRepository extends JpaRepository<FavouriteStudent, Long> {

    /**
     * Deletes a {@link FavouriteStudent} by owner ID and student ID.
     */
    void deleteFavouriteStudentByOwnerIdAndStudentId(Long ownerId, Long studentId);

    /**
     * Finds all favourite students by owner ID.
     */
    List<FavouriteStudent> findFavouriteStudentsByOwnerId(Long ownerId);
}
