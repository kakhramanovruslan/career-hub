package com.project.studentservice.util;

import com.project.studentservice.model.entity.Student;
import com.project.studentservice.model.types.DegreeEnum;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for building dynamic JPA specifications (queries) for filtering students.
 * The specifications can filter students based on various criteria, such as first name, last name,
 * degree, current year, GPA range, and university ID.
 */
public class StudentSpecification {

    /**
     * Creates a Specification for filtering students based on various optional parameters.
     *
     * @param firstName the student's first name (optional).
     * @param lastName the student's last name (optional).
     * @param degree the student's degree (optional).
     * @param currentYear the student's current year (optional).
     * @param universityId the university ID the student is associated with (optional).
     * @param minGpa the minimum GPA (optional).
     * @param maxGpa the maximum GPA (optional).
     * @return a Specification for filtering students.
     */
    public static Specification<Student> withFilters(String firstName, String lastName, DegreeEnum degree, Integer currentYear, Long universityId, Double minGpa, Double maxGpa) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by first name
            Optional.ofNullable(firstName)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n -> predicates.add(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + n.toLowerCase() + "%")
                    ));

            // Filter by last name
            Optional.ofNullable(lastName)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n -> predicates.add(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + n.toLowerCase() + "%")
                    ));

            // Filter by degree
            Optional.ofNullable(degree)
                    .ifPresent(d -> predicates.add(
                            criteriaBuilder.equal(root.get("degree"), d)
                    ));

            // Filter by current year
            Optional.ofNullable(currentYear)
                    .ifPresent(year -> predicates.add(
                            criteriaBuilder.equal(root.get("currentYear"), year)
                    ));

            // Filter by GPA range
            if (minGpa != null && maxGpa != null) {
                predicates.add(criteriaBuilder.between(root.get("gpa"), minGpa, maxGpa));
            } else if (minGpa != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("gpa"), minGpa));
            } else if (maxGpa != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("gpa"), maxGpa));
            }

            // Filter by university ID
            Optional.ofNullable(universityId)
                    .ifPresent(id -> predicates.add(
                            criteriaBuilder.equal(root.get("universityId"), id)
                    ));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
