package com.project.studentservice.model.dto;

import java.time.Instant;
import java.util.UUID;

public record SearchResponseDto(
        UUID searchQueryId,
        Long companyId,
        String queryText,
        Instant createdAt
) {}