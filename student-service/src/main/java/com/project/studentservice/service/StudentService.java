package com.project.studentservice.service;


import com.project.studentservice.model.dto.StudentDto;

public interface StudentService {

    public StudentDto findStudentById(Long id);
}
