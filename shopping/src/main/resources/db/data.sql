-- INSERT INTO user_tb (username,
--                      password,
--                      nickname,
--                      email,
--
--                      phone_number,
--                      gender,
--                      birthday,
--                      created_at
-- )
-- VALUES ('user01',
--         '1234567',
--         '유저1',
--         'u1@test.com',
--         '010-1111-2222',
--         'M',
--         '2000-01-01',
--         now()),
--        ('user02',
--         '1234567',
--         '유저2',
--         'u2@test.com',
--         '010-2222-3333',
--         'F',
--         null,
--         now()),
--        ('user03',
--         '1234567',
--         '유저3',
--         'u3@test.com',
--         '010-3333-4444',
--         'M',
--         '1999-03-03',
--         now()
--        );
--
-- INSERT INTO cart_tb (cart_price, user_id) VALUES
--                                               (0, 1),
--                                               (0, 2),
--                                               (0, 3);
--
--

--
-- INSERT INTO categories (category_name, depth, display_order, created_at)
-- VALUES
--
--
--     ('상의', 1, 1, now()),
--     ('하의', 1, 2, now()),
--     ('신발', 1, 3, now()),
--     ('모자', 1, 4, now());
--
--
-- INSERT INTO product_tb (
--     product_name,
--     product_code,
--     price,
--     stock_quantity,
--     description,
--     thumbnail_url,
--     status,
--     category_id,
--     created_at
-- )
-- VALUES
--     (
--         '무선 키보드',
--         'KB-001',
--         45000,
--         50,
--         '타이핑감이 좋은 무선 키보드',
--         '/img/product/keyboard.jpg',
--         'ACTIVE',
--         1,
--         '2025-12-10 09:00:00'
--     ),
--     (
--         '무선 마우스',
--         'MS-002',
--         30000,
--         80,
--         '정확한 센서의 무선 마우스',
--         '/img/product/mouse.jpg',
--         'ACTIVE',
--         1,
--         '2025-12-11 10:30:00'
--     ),
--     (
--         '27인치 모니터',
--         'MN-003',
--         280000,
--         20,
--         'FHD 27인치 모니터',
--         '/img/product/monitor.jpg',
--         'ACTIVE',
--         2,
--         '2025-12-12 14:15:00'
--     );
--
-- INSERT INTO product_tb
-- (product_name, product_code, price, stock_quantity, description, thumbnail_url, status, category_id, created_at)
-- VALUES
--     ('베이직 반팔 티셔츠', 'TSHIRT-001', 15000.00, 100, '부드러운 면 소재의 기본 반팔 티셔츠',
--      'thumb_tshirt_1.jpg', 'ACTIVE', 1, NOW()),
--
--     ('데일리 후드티', 'HOODIE-001', 32000.00, 50, '캐주얼 데일리용 후드티',
--      'thumb_hoodie_1.jpg', 'ACTIVE', 1, NOW()),
--
--     ('슬림 데님 팬츠', 'JEANS-001', 45000.00, 30, '슬림핏 청바지',
--      'thumb_jeans_1.jpg', 'ACTIVE', 2, NOW()),
--
--     ('코튼 팬츠', 'PANTS-001', 38000.00, 40, '편안한 착용감의 면 바지',
--      'thumb_pants_1.jpg', 'ACTIVE', 2, NOW()),
--
--     ('베이직 양말', 'SOCKS-001', 12000.00, 200, '사계절용 기본 양말',
--      'thumb_socks_1.jpg', 'ACTIVE', 3, NOW()),
--
--     ('윈드브레이커', 'JACKET-001', 55000.00, 20, '가벼운 바람막이 자켓',
--      'thumb_jacket_1.jpg', 'ACTIVE', 4, NOW()),
--
--     ('다운 패딩', 'PADDING-001', 89000.00, 15, '겨울용 패딩 자켓',
--      'thumb_padding_1.jpg', 'ACTIVE', 4, NOW()),
--
--     ('베이직 볼캡', 'CAP-001', 27000.00, 60, '심플한 디자인의 볼캡',
--      'thumb_cap_1.jpg', 'ACTIVE', 3, NOW()),
--
--     ('캔버스 에코백', 'BAG-001', 22000.00, 80, '데일리용 에코백',
--      'thumb_bag_1.jpg', 'ACTIVE', 3, NOW()),
--
--     ('나시 티셔츠', 'SLEEVELESS-001', 18000.00, 0, '여름용 민소매 티셔츠',
--      'thumb_sleeveless_1.jpg', 'SOLD_OUT', 1, NOW());


-- User 데이터 (비밀번호는 예시로 '1234' 가정)
INSERT INTO user_tb (id, username, password, nickname, email, phone_number, gender, birthday, provider, created_at, updated_at) VALUES
                                                                                                                                    (1, 'user01', '12341234', '관리자', 'admin@example.com', '010-1111-1111', 'N', '1990-01-01', 'LOCAL', NOW(), NOW()),
                                                                                                                                    (2, 'user02', '12341234', '대박판매자', 'seller01@example.com', '010-2222-2222', 'M', '1985-05-20', 'LOCAL', NOW(), NOW()),
                                                                                                                                    (3, 'user03', '12341234', '쇼핑왕', 'user01@example.com', '010-3333-3333', 'F', '1995-12-25', 'LOCAL', NOW(), NOW()),
                                                                                                                                    (4, 'user04', '12341234', '새오너', 'user04@example.com', '010-4444-4444', 'F', '1995-12-25', 'LOCAL', NOW(), NOW())

;

INSERT INTO user_role_tb (role, user_id) VALUES ('ADMIN', 1), ('OWNER', 2), ('USER', 3), ('OWNER', 4);

-- Owner (판매자 등록)
INSERT INTO user_owner_tb (id, user_id, name, status) VALUES
    (1, 2, '대박상사', 'APPROVED'),
    (2, 4, '새오너', 'APPROVED');

INSERT INTO cart_tb (amount, user_id) VALUES
                                              (0, 1),
                                              (0, 2),
                                              (0, 3);


-- 대분류
INSERT INTO categories (id, category_name, depth, display_order, parent_id, created_at, updated_at) VALUES
                                                                                                        (1, '패션의류', 1, 1, NULL, NOW(), NOW()),
                                                                                                        (2, '가전디지털', 1, 2, NULL, NOW(), NOW());

-- 소분류
INSERT INTO categories (id, category_name, depth, display_order, parent_id, created_at, updated_at) VALUES
                                                                                                        (3, '남성상의', 2, 1, 1, NOW(), NOW()),
                                                                                                        (4, '여성상의', 2, 2, 1, NOW(), NOW()),
                                                                                                        (5, '노트북', 2, 1, 2, NOW(), NOW());

-- product_tb 데이터 (허용된 Enum 값: ACTIVE, SOLD_OUT 사용)
INSERT INTO product_tb (owner_id, category_id, product_name, product_code, price, stock_quantity, status, thumbnail_url, description, created_at) VALUES
                                                                                                                                                      (1, 1, '베이직 반팔 티셔츠', 'TSHIRT-001', 15000, 100, 'ACTIVE', 'https://picsum.photos/seed/1/200', '부드러운 면 소재의 기본 반팔 티셔츠', NOW()),
                                                                                                                                                      (1, 1, '데일리 후드티', 'HOODIE-001', 32000, 50, 'ACTIVE', 'https://picsum.photos/seed/2/200', '캐주얼 데일리용 후드티', NOW()),
                                                                                                                                                      (1, 2, '슬림 데님 팬츠', 'JEANS-001', 45000, 30, 'ACTIVE', 'https://picsum.photos/seed/3/200', '슬림핏 청바지', NOW()),
                                                                                                                                                      (1, 2, '코튼 팬츠', 'PANTS-001', 38000, 40, 'ACTIVE', 'https://picsum.photos/seed/4/200', '편안한 착용감의 면 바지', NOW()),
                                                                                                                                                      (1, 3, '베이직 양말 세트', 'SOCKS-001', 12000, 200, 'ACTIVE', 'https://picsum.photos/seed/5/200', '사계절용 기본 양말 5켤레', NOW()),
                                                                                                                                                      (1, 4, '윈드브레이커', 'JACKET-001', 55000, 20, 'ACTIVE', 'https://picsum.photos/seed/6/200', '가벼운 바람막이 자켓', NOW()),
                                                                                                                                                      (1, 4, '경량 다운 패딩', 'PADDING-001', 89000, 15, 'ACTIVE', 'https://picsum.photos/seed/7/200', '겨울용 가벼운 패딩 자켓', NOW()),
                                                                                                                                                      (1, 3, '베이직 볼캡', 'CAP-001', 27000, 60, 'ACTIVE', 'https://picsum.photos/seed/8/200', '심플한 디자인의 볼캡', NOW()),
                                                                                                                                                      (1, 3, '캔버스 에코백', 'BAG-001', 22000, 80, 'ACTIVE', 'https://picsum.photos/seed/9/200', '데일리용 에코백', NOW()),
                                                                                                                                                      (2, 1, '나시 티셔츠', 'SLEEVELESS-001', 18000, 0, 'SOLD_OUT', 'https://picsum.photos/seed/10/200', '여름용 민소매 티셔츠', NOW()),
                                                                                                                                                      (2, 1, '울 가디건', 'CARDIGAN-001', 49000, 25, 'ACTIVE', 'https://picsum.photos/seed/11/200', '따뜻한 울 혼방 가디건', NOW()),
                                                                                                                                                      (2, 1, '체크 셔츠', 'SHIRT-001', 35000, 45, 'ACTIVE', 'https://picsum.photos/seed/12/200', '클래식한 체크 패턴 셔츠', NOW()),
                                                                                                                                                      (2, 2, '와이드 슬랙스', 'SLACKS-001', 42000, 35, 'ACTIVE', 'https://picsum.photos/seed/13/200', '트렌디한 와이드 핏 슬랙스', NOW()),
                                                                                                                                                      (2, 1, '트렌치 코트', 'COAT-001', 120000, 10, 'ACTIVE', 'https://picsum.photos/seed/14/200', '봄 가을용 클래식 트렌치 코트', NOW()),
                                                                                                                                                      (2, 3, '가죽 벨트', 'BELT-001', 25000, 50, 'ACTIVE', 'https://picsum.photos/seed/15/200', '천연 소가죽 정장 벨트', NOW()),
                                                                                                                                                      (2, 1, '그래픽 티셔츠', 'TSHIRT-002', 22000, 70, 'ACTIVE', 'https://picsum.photos/seed/16/200', '스트릿 감성의 그래픽 티셔츠', NOW()),
                                                                                                                                                      (2, 2, '조거 팬츠', 'PANTS-002', 33000, 60, 'ACTIVE', 'https://picsum.photos/seed/17/200', '활동성이 좋은 조거 트레이닝 바지', NOW()),
                                                                                                                                                      (2, 1, '터틀넥 니트', 'KNIT-001', 38000, 20, 'ACTIVE', 'https://picsum.photos/seed/18/200', '부드러운 촉감의 터틀넥 니트', NOW()),
                                                                                                                                                      (2, 1, '레더 자켓', 'JACKET-002', 95000, 12, 'ACTIVE', 'https://picsum.photos/seed/19/200', '세련된 인조 가죽 자켓', NOW()),
                                                                                                                                                      (2, 3, '비니 모자', 'BEANIE-001', 15000, 100, 'ACTIVE', 'https://picsum.photos/seed/20/200', '겨울철 필수 아이템 골지 비니', NOW());


