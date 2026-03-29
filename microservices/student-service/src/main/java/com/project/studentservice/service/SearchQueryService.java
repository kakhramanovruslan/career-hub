package com.project.studentservice.service;

import com.project.studentservice.model.dto.SearchRequestDto;
import com.project.studentservice.model.dto.SearchResponseDto;

public interface SearchQueryService {

    SearchResponseDto save(SearchRequestDto request);
}
