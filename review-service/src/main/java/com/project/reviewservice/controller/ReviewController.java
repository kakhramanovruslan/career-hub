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


@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/getAll/{recipientId}")
    public ResponseEntity<Page<ReviewDto>> getReviews(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @PathVariable Long recipientId){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok().body(reviewService.getReviewsByRecipientId(recipientId, pageable));
    }

    @GetMapping("/getAverageRating/{recipientId}")
    public ResponseEntity<AverageRatingResponse> getAverageRating(@PathVariable Long recipientId){
        return ResponseEntity.ok().body(reviewService.getAverageRating(recipientId));
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewDto> addReview(@RequestBody  ReviewRequest reviewRequest,
                                               @RequestHeader("X-User-Role") UserRole role,
                                               @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok().body(reviewService.addReview(reviewRequest, userId, role));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateReview(@PathVariable Long id,
                                             @RequestBody ReviewUpdateRequest reviewUpdateRequest,
                                             @RequestHeader("X-User-Role") UserRole role,
                                             @RequestHeader("X-User-Id") Long userId) {
        reviewService.updateReview(id, reviewUpdateRequest, userId, role);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                             @RequestHeader("X-User-Role") UserRole role,
                                             @RequestHeader("X-User-Id") Long userId) {
        reviewService.deleteReview(id, userId, role);
        return ResponseEntity.ok().build();
    }

}
