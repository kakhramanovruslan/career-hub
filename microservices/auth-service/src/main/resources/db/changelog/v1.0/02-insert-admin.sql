--liquibase formatted sql

-- changeset ruslan:3
INSERT INTO auth_schema.users (username, password, role, email, created_at, updated_at)
VALUES ('admin', '$2a$10$GvBvaTxMSerRWvcVmLg5Ju4t1JhOmhU.pbA7NJ5hwgDgDRxFcVEu6', 'ADMIN', 'admin@admin',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
