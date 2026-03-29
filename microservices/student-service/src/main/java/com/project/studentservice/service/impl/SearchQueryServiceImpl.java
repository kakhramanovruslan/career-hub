package com.project.studentservice.service.impl;

import com.project.studentservice.model.dto.SearchRequestDto;
import com.project.studentservice.model.dto.SearchResponseDto;
import com.project.studentservice.model.entity.CompanySearchQuery;
import com.project.studentservice.repository.SearchQueryRepository;
import com.project.studentservice.service.SearchQueryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchQueryServiceImpl implements SearchQueryService {

    private static final int MAX_HISTORY_SIZE = 10;

    private final SearchQueryRepository repository;

    @Transactional
    public SearchResponseDto save(SearchRequestDto request) {
        String normalized = normalize(request.queryText());

        CompanySearchQuery entity = new CompanySearchQuery();
        entity.setCompanyId(request.companyId());
        entity.setQueryText(normalized);

        CompanySearchQuery saved = repository.save(entity);

        trimToWindow(request.companyId());

        return new SearchResponseDto(
                saved.getId(),
                saved.getCompanyId(),
                saved.getQueryText(),
                saved.getCreatedAt()
        );
    }

    private void trimToWindow(Long companyId) {
        long count = repository.countByCompanyId(companyId);
        if (count <= MAX_HISTORY_SIZE) {
            return;
        }

        int overflow = (int) (count - MAX_HISTORY_SIZE);
        List<CompanySearchQuery> oldest = repository.findByCompanyIdOrderByCreatedAtAsc(
                companyId,
                PageRequest.of(0, overflow)
        );

        repository.deleteAll(oldest);
    }

    private String normalize(String text) {
        if (text == null) {
            throw new IllegalArgumentException("queryText is required");
        }
        String normalized = text.trim().replaceAll("\\s+", " ");
        if (normalized.isBlank()) {
            throw new IllegalArgumentException("queryText is blank");
        }
        return normalized;
    }
}