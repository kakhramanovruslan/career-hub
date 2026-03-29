package com.project.studentservice.service;

import com.project.studentservice.model.dto.ResumeRequestDto;

import java.io.IOException;

public interface ResumeService {

    void indexResume(ResumeRequestDto request) throws IOException;
}
