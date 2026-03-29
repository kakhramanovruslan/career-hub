package com.project.studentservice.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ResumeIndexInitializer {

    private static final String INDEX_NAME = "student_resumes";

    private final ElasticsearchClient client;

    public ResumeIndexInitializer(ElasticsearchClient client) {
        this.client = client;
    }

    @PostConstruct
    public void init() throws IOException {
        boolean exists = client.indices().exists(e -> e.index(INDEX_NAME)).value();
        if (exists) {
            return;
        }

        client.indices().create(c -> c
                .index(INDEX_NAME)
                .mappings(m -> m
                        .properties("id", p -> p.keyword(k -> k))
                        .properties("skills", p -> p.text(t -> t))
                        .properties("experience", p -> p.text(t -> t))
                )
        );
    }
}