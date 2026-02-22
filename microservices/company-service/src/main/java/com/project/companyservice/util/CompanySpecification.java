package com.project.companyservice.util;

import com.project.companyservice.model.entity.Company;
import com.project.companyservice.model.enums.CompanyType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class for creating JPA Specifications with various filters for querying Company entities.
 */
public class CompanySpecification {

    /**
     * Creates a Specification for filtering Company entities based on various criteria.
     *
     * @param name The name of the company.
     * @param type The type of the company.
     * @param location The location of the company.
     * @param industry The industry of the company.
     * @return A Specification that can be used to filter Company entities.
     */
    public static Specification<Company> withFilters(String name, CompanyType type, String location, String industry) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by name (partial match, case insensitive)
            Optional.ofNullable(name)
                    .filter(n -> !n.isEmpty())
                    .ifPresent(n ->
                            predicates.add(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + n.toLowerCase() + "%")
                            )
                    );

            // Filter by type (exact match, case insensitive)
            Optional.ofNullable(type)
                    .ifPresent(t ->
                            predicates.add(
                                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("type").as(String.class)), t.toString().toLowerCase())
                            )
                    );

            // Filter by location (exact match, case insensitive)
            Optional.ofNullable(location)
                    .filter(l -> !l.isEmpty())
                    .ifPresent(l ->
                            predicates.add(
                                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("location")), l.toLowerCase())
                            )
                    );

            // Filter by industry (partial match, case insensitive)
            Optional.ofNullable(industry)
                    .filter(i -> !i.isEmpty())
                    .ifPresent(i ->
                            predicates.add(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("industry")), "%" + i.toLowerCase() + "%")
                            )
                    );

            // Combine all predicates with an AND condition
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
