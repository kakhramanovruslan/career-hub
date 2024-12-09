package com.project.reviewservice.service.impl;

import com.project.reviewservice.exception.AccessDeniedException;
import com.project.reviewservice.exception.ReviewNotFoundException;
import com.project.reviewservice.mapper.ReviewDtoMapper;
import com.project.reviewservice.mapper.ReviewRequestMapper;
import com.project.reviewservice.mapper.ReviewUpdateRequestMapper;
import com.project.reviewservice.model.dto.AverageRatingResponse;
import com.project.reviewservice.model.dto.ReviewDto;
import com.project.reviewservice.model.dto.ReviewRequest;
import com.project.reviewservice.model.dto.ReviewUpdateRequest;
import com.project.reviewservice.model.entity.Review;
import com.project.reviewservice.model.enums.UserRole;
import com.project.reviewservice.repository.ReviewRepository;
import com.project.reviewservice.service.ReviewService;
import com.project.reviewservice.util.ExceptionMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewRequestMapper reviewRequestMapper;
    private final ReviewDtoMapper reviewDtoMapper;
    private final ReviewUpdateRequestMapper reviewUpdateRequestMapper;

    @Override
    public ReviewDto addReview(ReviewRequest reviewRequest, Long userId, UserRole role) {
        Review review = reviewRepository.save(reviewRequestMapper.toEntity(reviewRequest, userId));
        return reviewDtoMapper.toDto(review);
    }

    @Override
    public void updateReview(Long id, ReviewUpdateRequest reviewUpdateRequest, Long userId, UserRole role) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(ExceptionMessages.REVIEW_NOT_FOUND));
        isOwner(userId, review.getSenderId());

        reviewRepository.save(reviewUpdateRequestMapper.updateReviewFromRequest(reviewUpdateRequest, review));
    }

    @Override
    public void deleteReview(Long id, Long userId, UserRole role) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(ExceptionMessages.REVIEW_NOT_FOUND));
        isOwner(userId, review.getSenderId());

        reviewRepository.delete(review);
    }

    @Override
    public Page<ReviewDto> getReviewsByRecipientId(Long recipientId, Pageable pageable) {
        Page<Review> companies = reviewRepository.findAllByRecipientId(recipientId, pageable);
        return companies.map(reviewDtoMapper::toDto);
    }

    @Override
    public AverageRatingResponse getAverageRating(Long recipientId) {
        Double averageRating = reviewRepository.getAverageRatingByRecipientId(recipientId);
        Long totalCount = reviewRepository.getTotalCountByRecipientId(recipientId);

        return AverageRatingResponse.builder()
                .averageRating(averageRating)
                .totalCount(totalCount)
                .build();
    }

    private void isOwner(Long id, Long ownerId) throws AccessDeniedException {
        if (!id.equals(ownerId)) throw new AccessDeniedException(ExceptionMessages.ACCESS_DENIED);
    }
}
