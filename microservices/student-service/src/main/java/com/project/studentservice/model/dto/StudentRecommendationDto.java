package com.project.studentservice.model.dto;

import java.util.List;

public record StudentRecommendationDto(
        String id,
        List<String> skills,
        List<String> experience
) {}

