-- V2__insert_sample_users.sql
-- Insert one sample user for integration tests
INSERT INTO users (id, email, password, username)
VALUES (1, 'sample.user@example.com', 'password123', 'sampleuser');

