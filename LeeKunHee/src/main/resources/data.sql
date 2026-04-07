-- 테스트용 유저 데이터 (서버 실행 시 자동 삽입)
INSERT INTO users (user_id, email, name, nickname, password, created_at, updated_at)
VALUES (1, 'test@test.com', '강아지', '가나디', '1234', NOW(), NOW());