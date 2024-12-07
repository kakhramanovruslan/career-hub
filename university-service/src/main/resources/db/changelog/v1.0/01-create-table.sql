CREATE TABLE IF NOT EXISTS university (
    id SERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL UNIQUE,
    name VARCHAR(100),
    type VARCHAR(50) CHECK ( type IN ('PRIVATE', 'STATE', 'NONE', 'COLLEGE')),
    email VARCHAR(100) NOT NULL UNIQUE,
    contact_phone VARCHAR(20),
    location VARCHAR(255),
    about_us TEXT,
    established_year INT CHECK (established_year <= EXTRACT(YEAR FROM CURRENT_DATE)),
    website VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);