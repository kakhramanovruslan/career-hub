package com.project.reviewservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ReviewRequest {

    private Long recipientId;

    private String reviewText;

    private Integer rating;
}
