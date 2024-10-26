CREATE TABLE IF NOT EXISTS university (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    type VARCHAR(50) CHECK ( type IN ('PRIVATE', 'STATE')) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contact_phone VARCHAR(20),
    location VARCHAR(255) NOT NULL,
    established_year INT NOT NULL CHECK (established_year <= EXTRACT(YEAR FROM CURRENT_DATE)),
    website VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
--
-- CREATE OR REPLACE FUNCTION update_timestamp()
--     RETURNS TRIGGER AS $$
-- BEGIN
--     NEW.updated_at = CURRENT_TIMESTAMP;
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;
--
-- CREATE TRIGGER trigger_update_timestamp
--     BEFORE UPDATE ON university
--     FOR EACH ROW
-- EXECUTE FUNCTION update_timestamp();