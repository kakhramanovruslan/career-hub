package com.project.studentservice.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.project.studentservice.model.document.StudentResumeDocument;
import com.project.studentservice.model.dto.ResumeRequestDto;
import com.project.studentservice.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private static final String INDEX_NAME = "student_resumes";

    private final ElasticsearchClient elasticsearchClient;

    public void indexResume(ResumeRequestDto request) throws IOException {
        StudentResumeDocument document = new StudentResumeDocument(
                request.id(),
                request.skills(),
                request.experience()
        );

        elasticsearchClient.index(i -> i
                .index(INDEX_NAME)
                .id(document.getId())
                .refresh(Refresh.WaitFor)
                .document(document)
        );
    }
}