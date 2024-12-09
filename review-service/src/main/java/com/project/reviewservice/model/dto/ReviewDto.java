package com.project.reviewservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewDto {

    private Long id;

    private Long recipientId;

    private Long senderId;

    private String reviewText;

    private Integer rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
