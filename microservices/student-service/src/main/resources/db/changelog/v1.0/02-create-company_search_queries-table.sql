--liquibase formatted sql

-- changeset bekzhan:11
CREATE TABLE company_search_queries (
    id UUID PRIMARY KEY,
    company_id BIGINT NOT NULL,
    query_text VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_company_search_queries_company_created_at
    ON company_search_queries (company_id, created_at);