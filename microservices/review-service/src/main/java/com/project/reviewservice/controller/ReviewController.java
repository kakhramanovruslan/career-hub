package com.project.reviewservice.controller;

import com.project.reviewservice.model.dto.AverageRatingResponse;
import com.project.reviewservice.model.dto.ReviewDto;
import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.dto.ReviewUpdateRequest;
import com.project.reviewservice.model.enums.UserRole;
import com.project.reviewservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling review-related requests.
 * Provides endpoints for getting, adding, updating, and deleting reviews,
 * as well as retrieving average ratings for a recipient.
 */
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * Retrieves all reviews for a given recipient, with pagination.
     *
     * @param page The page number (default is 0).
     * @param size The page size (default is 10).
     * @param recipientId The ID of the recipient whose reviews are being fetched.
     * @return A paginated list of reviews for the recipient.
     */
    @GetMapping("/getAll/{recipientId}")
    public ResponseEntity<Page<ReviewDto>> getReviews(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @PathVariable Long recipientId) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(reviewService.getReviewsByRecipientId(recipientId, pageable));
    }

    /**
     * Retrieves the average rating for a given recipient.
     *
     * @param recipientId The ID of the recipient whose average rating is being fetched.
     * @return The average rating response for the recipient.
     */
    @GetMapping("/getAverageRating/{recipientId}")
    public ResponseEntity<AverageRatingResponse> getAverageRating(@PathVariable Long recipientId) {
        return ResponseEntity.ok().body(reviewService.getAverageRating(recipientId));
    }

    /**
     * Adds a new review for a recipient.
     *
     * @param reviewRequest The request body containing review details.
     * @param role The role of the user submitting the review.
     * @param userId The ID of the user submitting the review.
     * @return The added review details.
     */
    @PostMapping("/add")
    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewRequest reviewRequest,
                                               @RequestHeader("X-User-Role") UserRole role,
                                               @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok().body(reviewService.addReview(reviewRequest, userId, role));
    }

    /**
     * Updates an existing review.
     *
     * @param id The ID of the review to be updated.
     * @param reviewUpdateRequest The request body containing updated review details.
     * @param role The role of the user updating the review.
     * @param userId The ID of the user updating the review.
     * @return A response indicating the update was successful.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Long id,
                                             @RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                             @RequestHeader("X-User-Role") UserRole role,
                                             @RequestHeader("X-User-Id") Long userId) {
        reviewService.updateReview(id, reviewUpdateRequest, userId, role);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a review by its ID.
     *
     * @param id The ID of the review to be deleted.
     * @param role The role of the user deleting the review.
     * @param userId The ID of the user deleting the review.
     * @return A response indicating the deletion was successful.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             @RequestHeader("X-User-Role") UserRole role,
                                             @RequestHeader("X-User-Id") Long userId) {
        reviewService.deleteReview(id, userId, role);
        return ResponseEntity.ok().build();
    }
}
