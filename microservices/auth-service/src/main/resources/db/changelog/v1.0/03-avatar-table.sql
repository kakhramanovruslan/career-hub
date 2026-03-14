--liquibase formatted sql

-- changeset olzhas:2
CREATE TABLE IF NOT EXISTS profile_photo
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    file_data    BYTEA  NOT NULL,
    content_type VARCHAR(50),
    file_name    VARCHAR(255),
    created_at   TIMESTAMP DEFAULT now()
);