package com.project.universityservice.util;

import com.project.universityservice.model.entity.University;
import com.project.universityservice.model.enums.UniversityType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A utility class that provides a method to build dynamic query filters for University entities.
 * This class is used for filtering universities based on various optional criteria such as
 * name, type, and location. It is typically used in conjunction with Spring Data JPA Specifications.
 */
public class UniversitySpecification {

    /**
     * Constructs a Specification for querying University entities based on provided filter criteria.
     *
     * @param name The name of the university to filter by. If null or empty, this filter is ignored.
     * @param type The type of the university to filter by (e.g., public, private). If null, this filter is ignored.
     * @param location The location of the university to filter by. If null or empty, this filter is ignored.
     * @return A Specification that can be used to filter universities based on the provided criteria.
     */
    public static Specification<University> withFilters(String name, UniversityType type, String location){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by university name if provided
            Optional.ofNullable(name)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n -> predicates.add(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n.toLowerCase() + "%")
                    ));

            // Filter by university type if provided
            Optional.ofNullable(type)
                    .ifPresent(t -> predicates.add(
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("type").as(String.class)), t.toString().toLowerCase())
                    ));

            // Filter by location if provided
            Optional.ofNullable(location)
                    .filter(l -> !l.isEmpty())
                    .ifPresent(l -> predicates.add(
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("location")), l.toLowerCase())
                    ));

            // Combine all predicates and return the Specification
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
