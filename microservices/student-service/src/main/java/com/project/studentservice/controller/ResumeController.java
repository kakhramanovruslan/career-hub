package com.project.studentservice.controller;

import com.project.studentservice.model.dto.ResumeRequestDto;
import com.project.studentservice.service.ResumeService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("student/api/resumes")
public class ResumeController {

    private final ResumeService service;

    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @PostMapping
    public void index(@RequestBody ResumeRequestDto request) throws IOException {
        service.indexResume(request);
    }
}