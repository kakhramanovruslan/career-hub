package com.project.studentservice.service;

import com.project.studentservice.model.dto.RecommendationResponseDto;

import java.io.IOException;
import java.util.List;

public interface RecommendationService {

    RecommendationResponseDto getRecommendations(Long companyId) throws IOException;

    List<Long> searchStudents(String query) throws IOException;
}
