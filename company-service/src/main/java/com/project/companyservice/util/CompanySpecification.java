package com.project.companyservice.util;

import com.project.companyservice.model.Company;
import com.project.companyservice.model.enums.CompanyType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanySpecification {
    public static Specification<Company> withFilters(String name, CompanyType type, String location, String industry){
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
                    .ifPresent(l ->
                            predicates.add(
                                    criteriaBuilder.equal(criteriaBuilder.lower(root.get("location")), l.toLowerCase())
                    ));

            Optional.ofNullable(industry)
                    .filter(i -> !i.isEmpty())
                    .ifPresent(i ->
                            predicates.add(
                                    criteriaBuilder.like(criteriaBuilder.lower(root.get("industry")), "%" + i.toLowerCase() + "%")
                    ));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
