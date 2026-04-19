package com.project.studentservice.controller;

import com.project.studentservice.model.dto.RecommendationResponseDto;
import com.project.studentservice.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("student/api/recommendations")
public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }

    @GetMapping
    public RecommendationResponseDto get(@RequestParam Long companyId) throws IOException {
        return service.getRecommendations(companyId);
    }
}