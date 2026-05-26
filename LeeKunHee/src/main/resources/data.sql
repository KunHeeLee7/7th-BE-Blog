INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (1, 'test1@test.com', 'test user 1', 'tester1', '1234', NOW(), NOW());

INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (2, 'test2@test.com', 'test user 2', 'tester2', '1234', NOW(), NOW());

INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (3, 'test3@test.com', 'test user 3', 'tester3', '1234', NOW(), NOW());

INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (4, 'test4@test.com', 'test user 4', 'tester4', '1234', NOW(), NOW());

INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (5, 'test5@test.com', 'test user 5', 'tester5', '1234', NOW(), NOW());

INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (6, 'test6@test.com', 'test user 6', 'tester6', '1234', NOW(), NOW());

ALTER TABLE users ALTER COLUMN user_id RESTART WITH 7;
