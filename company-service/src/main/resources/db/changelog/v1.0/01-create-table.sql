--liquibase formatted sql

-- changeset olzhas:1
CREATE TABLE IF NOT EXISTS company (
    id SERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(255),
    type VARCHAR(50) CHECK ( type IN ('PRIVATE', 'STATE')),
    email VARCHAR(100) UNIQUE NOT NULL,
    location VARCHAR(255),
    contact_phone VARCHAR(20),
    industry VARCHAR(255),
    website VARCHAR(255),
    established_year INT CHECK (established_year <= EXTRACT(YEAR FROM CURRENT_DATE)),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- changeset olzhas:2
CREATE TABLE IF NOT EXISTS company_favourite_student_ids (
    id SERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    company_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    FOREIGN KEY (company_id) REFERENCES company(id) ON DELETE CASCADE,
    UNIQUE (company_id, student_id)
);