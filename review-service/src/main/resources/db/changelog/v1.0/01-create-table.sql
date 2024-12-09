--liquibase formatted sql

-- changeset ruslan:1
CREATE TABLE IF NOT EXISTS review (
    id SERIAL PRIMARY KEY,
    recipient_id BIGINT NOT NULL, -- ID аккаунта, кому оставили отзыв
    sender_id BIGINT NOT NULL,   -- ID аккаунта, кто оставил отзыв
    review_text TEXT,
    rating INT CHECK (rating BETWEEN 1 AND 5),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
