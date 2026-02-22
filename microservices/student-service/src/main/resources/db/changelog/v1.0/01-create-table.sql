--liquibase formatted sql

-- changeset ruslan:1
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL UNIQUE,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) NOT NULL UNIQUE,
    university_id BIGINT NOT NULL,
    gpa DECIMAL(3, 2) CHECK (gpa >= 0.0 AND gpa <= 4.0),
    phone_number VARCHAR(20),
    about_us TEXT,
    degree VARCHAR(20) CHECK (degree IN ('BACHELOR', 'MASTER', 'DOCTORATE')),
    current_year INT,
    year_of_enrollment INT CHECK (year_of_enrollment >= 2000 AND year_of_enrollment <= EXTRACT(YEAR FROM CURRENT_DATE)),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
