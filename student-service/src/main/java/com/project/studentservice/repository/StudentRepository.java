package com.project.studentservice.repository;

import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
