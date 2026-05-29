-- Пароль для всех: password123
-- $2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2 = password123

INSERT INTO users (username, email, password, name, bio, enabled,
                   post_count, followers_count, following_count)
VALUES
    ('admin',
     'admin@microgram.kg',
     '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2',
     'Администратор',
     'Главный администратор сайта',
     true, 0, 0, 0),

    ('john_doe',
     'john@microgram.kg',
     '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2',
     'John Doe',
     'Люблю фотографию и путешествия',
     true, 0, 0, 0),

    ('jane_doe',
     'jane@microgram.kg',
     '$2a$12$WB2YUbFcCN0tm44SBcKUjua9yiFBsfB3vW02IjuwzY7HGtlQIKzy2',
     'Jane Doe',
     'Путешественница и фотограф',
     true, 0, 0, 0);