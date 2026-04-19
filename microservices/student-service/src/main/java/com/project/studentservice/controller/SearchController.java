package com.project.studentservice.controller;

import com.project.studentservice.model.dto.SearchRequestDto;
import com.project.studentservice.model.dto.SearchResponseDto;
import com.project.studentservice.service.SearchQueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student/api/search")
public class SearchController {

    private final SearchQueryService service;

    public SearchController(SearchQueryService service) {
        this.service = service;
    }

    @PostMapping
    public SearchResponseDto save(@RequestBody SearchRequestDto request) {
        return service.save(request);
    }
}