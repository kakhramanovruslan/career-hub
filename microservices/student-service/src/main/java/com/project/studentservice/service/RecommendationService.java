package com.project.studentservice.service;

import com.project.studentservice.model.dto.RecommendationResponseDto;

import java.io.IOException;
import java.util.UUID;

public interface RecommendationService {

    RecommendationResponseDto getRecommendations(Long companyId) throws IOException;
}
