package com.project.studentservice.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.GetResponse;
import com.project.studentservice.model.document.StudentResumeDocument;
import com.project.studentservice.model.dto.ResumeRequestDto;
import com.project.studentservice.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private static final String INDEX_NAME = "student_resumes";

    private final ElasticsearchClient elasticsearchClient;

    public void indexResume(ResumeRequestDto request) throws IOException {
        StudentResumeDocument document = new StudentResumeDocument(
                request.studentId(),
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

    @Override
    public StudentResumeDocument getResume(String studentId) throws IOException {
        GetResponse<StudentResumeDocument> response = elasticsearchClient.get(g -> g
                        .index(INDEX_NAME)
                        .id(studentId),
                StudentResumeDocument.class
        );

        if (!response.found()) {
            throw new NoSuchElementException("Resume not found for studentId: " + studentId);
        }

        return response.source();
    }

    @Override
    public void updateResume(String studentId, ResumeRequestDto request) throws IOException {
        StudentResumeDocument updatedDocument = new StudentResumeDocument(
                studentId,
                request.skills(),
                request.experience()
        );

        elasticsearchClient.update(u -> u
                        .index(INDEX_NAME)
                        .id(studentId)
                        .refresh(Refresh.WaitFor)
                        .doc(updatedDocument),
                StudentResumeDocument.class
        );
    }
}