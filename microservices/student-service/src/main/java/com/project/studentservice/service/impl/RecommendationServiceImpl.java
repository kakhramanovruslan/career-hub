package com.project.studentservice.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.project.studentservice.model.document.StudentResumeDocument;
import com.project.studentservice.model.dto.RecommendationResponseDto;
import com.project.studentservice.model.dto.StudentRecommendationDto;
import com.project.studentservice.model.entity.CompanySearchQuery;
import com.project.studentservice.repository.SearchQueryRepository;
import com.project.studentservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {

    private static final String INDEX_NAME = "student_resumes";

    private final SearchQueryRepository searchQueryRepository;
    private final ElasticsearchClient elasticsearchClient;

    public RecommendationResponseDto getRecommendations(Long companyId) throws IOException {
        List<CompanySearchQuery> queries = searchQueryRepository
                .findTop10ByCompanyIdOrderByCreatedAtDesc(companyId);

        if (queries.isEmpty()) {
            return new RecommendationResponseDto(
                    false,
                    "После поиска у вас появятся персональные рекомендации",
                    null,
                    List.of()
            );
        }

        String combinedQuery = queries.stream()
                .sorted(Comparator.comparing(CompanySearchQuery::getCreatedAt))
                .map(CompanySearchQuery::getQueryText)
                .collect(Collectors.joining(" "));

        SearchResponse<StudentResumeDocument> response = elasticsearchClient.search(s -> s
                        .index(INDEX_NAME)
                        .size(10)
                        .query(q -> q.multiMatch(mm -> mm
                                .query(combinedQuery)
                                .fields("skills", "experience")
                        ))
                        .sort(so -> so.score(sc -> sc.order(SortOrder.Desc))),
                StudentResumeDocument.class
        );

        List<StudentRecommendationDto> students = response.hits().hits().stream()
                .map(Hit::source)
                .filter(java.util.Objects::nonNull)
                .limit(10)
                .map(doc -> new StudentRecommendationDto(
                        doc.getId(),
                        doc.getSkills(),
                        doc.getExperience()
                ))
                .toList();

        return new RecommendationResponseDto(
                true,
                null,
                combinedQuery,
                students
        );
    }
}