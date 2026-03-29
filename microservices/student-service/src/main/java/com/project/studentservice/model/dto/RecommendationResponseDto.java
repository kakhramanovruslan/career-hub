package com.project.studentservice.model.dto;

import java.util.List;

public record RecommendationResponseDto(
        boolean hasHistory,
        String message,
        String combinedQuery,
        List<StudentRecommendationDto> students
) {}