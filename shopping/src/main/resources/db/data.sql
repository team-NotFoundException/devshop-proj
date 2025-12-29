INSERT INTO user_tb (username,
                     password,
                     nickname,
                     email,
                     address,
                     phone_number,
                     gender,
                     birthday,
                     created_at
)
VALUES ('user01',
        '1234567',
        '유저1',
        'u1@test.com',
        '서울',
        '010-1111-2222',
        'M',
        '2000-01-01',
        now()),
       ('user02',
        '1234567',
        '유저2',
        'u2@test.com',
        '부산',
        '010-2222-3333',
        'F',
        null,
        now()),
       ('user03',
        '1234567',
        '유저3',
        'u3@test.com',
        '대구',
        '010-3333-4444',
        'M',
        '1999-03-03',
        now()
       );

INSERT INTO cart_tb (cart_price, user_id) VALUES
                                              (0, 1),
                                              (0, 2),
                                              (0, 3);


INSERT INTO user_role_tb (role, user_id) VALUES ('ADMIN', 1), ('OWNER', 2), ('USER', 3);

INSERT INTO categories (category_name, depth, display_order, created_at)
VALUES
    ('마우스', 1, 1, now()),
    ('키보드', 1, 2, now()),
    ('가디건', 1, 3, now()),
    ('신발', 1, 4, now());

INSERT INTO product_tb (
    product_name,
    product_code,
    price,
    stock_quantity,
    description,
    thumbnail_url,
    status,
    category_id,
    created_at
)
VALUES
    (
        '무선 키보드',
        'KB-001',
        45000,
        50,
        '타이핑감이 좋은 무선 키보드',
        '/img/product/keyboard.jpg',
        'ACTIVE',
        1,
        '2025-12-10 09:00:00'
    ),
    (
        '무선 마우스',
        'MS-002',
        30000,
        80,
        '정확한 센서의 무선 마우스',
        '/img/product/mouse.jpg',
        'ACTIVE',
        1,
        '2025-12-11 10:30:00'
    ),
    (
        '27인치 모니터',
        'MN-003',
        280000,
        20,
        'FHD 27인치 모니터',
        '/img/product/monitor.jpg',
        'ACTIVE',
        2,
        '2025-12-12 14:15:00'
    );

INSERT INTO product_tb
(product_name, product_code, price, stock_quantity, description, thumbnail_url, status, category_id, created_at)
VALUES
    ('베이직 반팔 티셔츠', 'TSHIRT-001', 15000.00, 100, '부드러운 면 소재의 기본 반팔 티셔츠',
     'thumb_tshirt_1.jpg', 'ACTIVE', 1, NOW()),

    ('데일리 후드티', 'HOODIE-001', 32000.00, 50, '캐주얼 데일리용 후드티',
     'thumb_hoodie_1.jpg', 'ACTIVE', 1, NOW()),

    ('슬림 데님 팬츠', 'JEANS-001', 45000.00, 30, '슬림핏 청바지',
     'thumb_jeans_1.jpg', 'ACTIVE', 2, NOW()),

    ('코튼 팬츠', 'PANTS-001', 38000.00, 40, '편안한 착용감의 면 바지',
     'thumb_pants_1.jpg', 'ACTIVE', 2, NOW()),

    ('베이직 양말', 'SOCKS-001', 12000.00, 200, '사계절용 기본 양말',
     'thumb_socks_1.jpg', 'ACTIVE', 3, NOW()),

    ('윈드브레이커', 'JACKET-001', 55000.00, 20, '가벼운 바람막이 자켓',
     'thumb_jacket_1.jpg', 'ACTIVE', 4, NOW()),

    ('다운 패딩', 'PADDING-001', 89000.00, 15, '겨울용 패딩 자켓',
     'thumb_padding_1.jpg', 'ACTIVE', 4, NOW()),

    ('베이직 볼캡', 'CAP-001', 27000.00, 60, '심플한 디자인의 볼캡',
     'thumb_cap_1.jpg', 'ACTIVE', 3, NOW()),

    ('캔버스 에코백', 'BAG-001', 22000.00, 80, '데일리용 에코백',
     'thumb_bag_1.jpg', 'ACTIVE', 3, NOW()),

    ('나시 티셔츠', 'SLEEVELESS-001', 18000.00, 0, '여름용 민소매 티셔츠',
     'thumb_sleeveless_1.jpg', 'SOLD_OUT', 1, NOW());
