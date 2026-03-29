package com.project.studentservice.model.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResumeDocument {
    private String id;
    private List<String> skills;
    private List<String> experience;
}