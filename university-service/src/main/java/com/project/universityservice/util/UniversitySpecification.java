package com.project.universityservice.util;

import com.project.universityservice.model.entity.University;
import com.project.universityservice.model.enums.UniversityType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UniversitySpecification {
    public static Specification<University> withFilters(String name, UniversityType type, String location){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(name)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n ->
                            predicates.add(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n.toLowerCase() + "%")
                            )
                    );

            Optional.ofNullable(type)
                    .ifPresent(t ->
                            predicates.add(
                                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("type").as(String.class)), t.toString().toLowerCase())
                    ));

            Optional.ofNullable(location)
                    .filter(l -> !l.isEmpty())
                    .ifPresent(l -> predicates.add(
                            criteriaBuilder.equal(criteriaBuilder.lower(root.get("location")), l.toLowerCase())
                    ));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
