package com.project.studentservice.model.dto;

public record SearchRequestDto(
        Long companyId,
        String queryText
) {}