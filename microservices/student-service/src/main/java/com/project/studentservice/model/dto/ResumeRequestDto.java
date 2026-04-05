package com.project.studentservice.model.dto;

import java.util.List;

public record ResumeRequestDto(
        String studentId,
        List<String> skills,
        List<String> experience
) {}