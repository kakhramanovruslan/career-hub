--liquibase formatted sql

-- changeset olzhas:4
CREATE TABLE IF NOT EXISTS auth_schema.profile_photo
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT NOT NULL REFERENCES auth_schema.users(id) ON DELETE CASCADE,
    file_data    BYTEA  NOT NULL,
    content_type VARCHAR(50),
    file_name    VARCHAR(255),
    created_at   TIMESTAMP DEFAULT now()
);