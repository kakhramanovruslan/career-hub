package com.project.reviewservice.service;

import com.project.reviewservice.model.dto.AverageRatingResponse;
import com.project.reviewservice.model.dto.ReviewDto;
import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.dto.ReviewUpdateRequest;
import com.project.reviewservice.model.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewDto addReview(ReviewRequest reviewRequest, Long userId, UserRole senderRole);

    void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest, Long userId, UserRole role);

    void deleteReview(Long id, Long userId, UserRole role);

    Page<ReviewDto> getReviewsByRecipientId(Long recipientId, Pageable pageable);

    AverageRatingResponse getAverageRating(Long recipientId);
}
