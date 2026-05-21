-- Пароль для всех: password123 (BCrypt)
INSERT INTO users (username, email, password, name, bio, enabled)
VALUES
    ('admin', 'admin@microgram.kg', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTvgqjjhUDW', 'Администратор', 'Главный администратор', true),
    ('john_doe', 'john@microgram.kg', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTvgqjjhUDW', 'John Doe', 'Люблю фотографию', true),
    ('jane_doe', 'jane@microgram.kg', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBpwTvgqjjhUDW', 'Jane Doe', 'Путешественница', true);