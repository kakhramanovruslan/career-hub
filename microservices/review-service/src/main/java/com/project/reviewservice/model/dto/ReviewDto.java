package com.project.reviewservice.model.dto;

import com.project.reviewservice.model.enums.UserRole;
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

    private UserRole senderRole;

    private UserRole recipientRole;

    private Integer rating;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
