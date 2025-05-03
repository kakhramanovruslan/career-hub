package com.project.reviewservice.repository;

import com.project.reviewservice.model.dto.AverageRatingResponse;
import com.project.reviewservice.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing {@link Review} entities from the database.
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom queries.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Retrieves a paginated list of {@link Review} entities for a given recipient.
     *
     * @param recipientId the ID of the recipient for which to find reviews.
     * @param pageable the pagination information.
     * @return a page of reviews associated with the given recipient ID.
     */
    Page<Review> findAllByRecipientId(Long recipientId, Pageable pageable);

    /**
     * Calculates the average rating for a given recipient.
     * If no ratings exist, it returns 0.0.
     *
     * @param recipientId the ID of the recipient for which to calculate the average rating.
     * @return the average rating of the recipient or 0.0 if no ratings exist.
     */
    @Query("SELECT COALESCE(AVG(r.rating), 0.0) FROM Review r WHERE r.recipientId = :recipientId")
    Double getAverageRatingByRecipientId(@Param("recipientId") Long recipientId);

    /**
     * Retrieves the total count of reviews for a given recipient.
     *
     * @param recipientId the ID of the recipient for which to count reviews.
     * @return the total number of reviews associated with the given recipient ID.
     */
    @Query("SELECT COUNT(r) FROM Review r WHERE r.recipientId = :recipientId")
    Long getTotalCountByRecipientId(@Param("recipientId") Long recipientId);

}
