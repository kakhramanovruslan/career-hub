package com.project.studentservice.util;

import com.project.studentservice.model.entity.Student;
import com.project.studentservice.model.types.DegreeEnum;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class StudentSpecification {
    public static Specification<Student> withFilters(String firstName, String lastName, DegreeEnum degree, Integer currentYear, Double minGpa, Double maxGpa){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Фильтр по имени
            Optional.ofNullable(firstName)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n ->
                            predicates.add(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + n.toLowerCase() + "%")
                            )
                    );

            // Фильтр по фамилии
            Optional.ofNullable(lastName)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n ->
                            predicates.add(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + n.toLowerCase() + "%")
                            )
                    );

            // Фильтр по степени
            Optional.ofNullable(degree)
                    .ifPresent(d ->
                            predicates.add(
                                    criteriaBuilder.equal(root.get("degree"), d)
                            )
                    );

            // Фильтр по текущему году
            Optional.ofNullable(currentYear)
                    .ifPresent(year ->
                            predicates.add(
                                    criteriaBuilder.equal(root.get("currentYear"), year)
                            )
                    );

            // Фильтр по диапазону GPA
            if (minGpa != null && maxGpa != null) {
                predicates.add(criteriaBuilder.between(root.get("gpa"), minGpa, maxGpa));
            } else if (minGpa != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("gpa"), minGpa));
            } else if (maxGpa != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("gpa"), maxGpa));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
