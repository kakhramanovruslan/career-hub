package com.project.reviewservice.service;

import com.project.reviewservice.model.dto.AverageRatingResponse;
import com.project.reviewservice.model.dto.ReviewDto;
import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.dto.ReviewUpdateRequest;
import com.project.reviewservice.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface for review-related services.
 * Provides methods for adding, updating, deleting reviews, fetching reviews by recipient,
 * and calculating average ratings for a recipient.
 */
public interface ReviewService {

    /**
     * Adds a new review.
     *
     * @param reviewRequest the review data from the client.
     * @param userId the ID of the user submitting the review.
     * @param senderRole the role of the user submitting the review.
     * @return the added review as a DTO.
     */
    ReviewDto addReview(ReviewRequest reviewRequest, Long userId, UserRole senderRole);

    /**
     * Updates an existing review.
     * Only the owner of the review is allowed to update it.
     *
     * @param id the ID of the review to be updated.
     * @param reviewUpdateRequest the updated review data.
     * @param userId the ID of the user requesting the update.
     * @param role the role of the user requesting the update.
     */
    void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest, Long userId, UserRole role);

    /**
     * Deletes an existing review.
     * Only the owner of the review is allowed to delete it.
     *
     * @param id the ID of the review to be deleted.
     * @param userId the ID of the user requesting the deletion.
     * @param role the role of the user requesting the deletion.
     */
    void deleteReview(Long id, Long userId, UserRole role);

    /**
     * Retrieves a paginated list of reviews for a specific recipient.
     *
     * @param recipientId the ID of the recipient.
     * @param pageable the pagination information.
     * @return a paginated list of reviews for the recipient.
     */
    Page<ReviewDto> getReviewsByRecipientId(Long recipientId, Pageable pageable);

    /**
     * Retrieves the average rating and total count of reviews for a specific recipient.
     *
     * @param recipientId the ID of the recipient.
     * @return the average rating and total count of reviews for the recipient.
     */
    AverageRatingResponse getAverageRating(Long recipientId);
}
