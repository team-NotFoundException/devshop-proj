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

INSERT INTO categories (category_name, depth, display_order, created_at)
VALUES
    ('상의', 1, 1, now()),
    ('하의', 1, 2, now()),
    ('신발', 1, 3, now());

INSERT INTO product_tb (
    product_name,
    product_code,
    price,
    stock_quantity,
    description,
    thumbnail_url,
    status,
    category_id,
    created_at,
    profile_image
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
        '2025-12-10 09:00:00',
        'keyboard_profile.jpg'
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
        '2025-12-11 10:30:00',
        'mouse_profile.jpg'
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
        '2025-12-12 14:15:00',
        'monitor_profile.jpg'
    );
INSERT INTO review_tb (
    user_id,
    product_id,
    content,
    rating,
    review_image,
    created_at
)
VALUES
    (
        1,
        1,
        '키감이 정말 좋고 배터리도 오래가요.',
        5,
        'review_keyboard.jpg',
        '2025-12-15 10:10:00'
    ),
    (
        2,
        2,
        '가볍고 사용하기 편해서 만족합니다.',
        4,
        NULL,
        '2025-12-16 09:40:00'
    ),
    (
        3,
        3,
        '화면이 크고 선명해서 작업하기 좋아요.',
        5,
        'review_monitor.jpg',
        '2025-12-17 18:05:00'
    );
