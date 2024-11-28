package com.project.studentservice.repository;

import com.project.studentservice.model.dto.StudentDto;
import com.project.studentservice.model.dto.StudentRequest;
import com.project.studentservice.model.entity.Student;
import com.project.studentservice.model.types.DegreeEnum;
import com.project.studentservice.util.StudentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>,
                                           JpaSpecificationExecutor<Student> {
    List<Student> findStudentByUniversityId(Long id, Pageable pageable);
    void deleteByOwnerId(Long id);
}
