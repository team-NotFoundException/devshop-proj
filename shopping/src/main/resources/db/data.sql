

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
INSERT INTO categories (id, category_name, depth, display_order, image_url, parent_id, created_at, updated_at) VALUES
                     (1, '패션의류', 1, 1, '/img/hoodie.jpg', NULL, NOW(), NOW()),
                     (2, '가전디지털', 1, 2,'/img/earbuds.jpg', NULL, NOW(), NOW());

-- 소분류
INSERT INTO categories (id, category_name, depth, display_order, parent_id, created_at, updated_at, image_url) VALUES
                                                                                                        (3, '반팔', 2, 1, 1, NOW(), NOW(),'/img/birdT.jpg'),
                                                                                                        (4, '머그컵', 2, 2, 1, NOW(), NOW(),'/img/mug.jpg'),
                                                                                                        (5, '노트북', 2, 1, 2, NOW(), NOW(),'/img/notebook.jpg');

-- product_tb 데이터 (허용된 Enum 값: ACTIVE, SOLD_OUT 사용)
INSERT INTO product_tb (owner_id, category_id, product_name, product_code, price, stock_quantity,minus_quantity, status, thumbnail_url, description, created_at) VALUES
                                                                                                                                                      (1, 1, 'Hyeku Aluminum Mechanical Keyboard', 'KEYBOARD-001', 118000, 100,20,  'ACTIVE', 'https://www.ipopularshop.com/cdn/shop/products/00f7438d4dadcc6dd1ddf6102d96d47a.jpg?v=1688442324', '알루미늄 키보드', NOW()),
                                                                                                                                                      (1, 1, '데일리 후드티', 'HOODIE-001', 32000, 50, 10, 'ACTIVE', 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=400', '캐주얼 데일리용 후드티', NOW()),
                                                                                                                                                      (1, 2, '오버사이즈 후드티', 'HOODIE-002', 45000, 30, 0,'ACTIVE', 'https://cafe24img.poxo.com/massnoun/web/product/big/202111/cc3c896bef814bcf59855c1cdf74da6d.jpg', '데일리 오버핏 후드티', NOW()),
                                                                                                                                                      (1, 2, '레트로 기계식 키보드', 'KEYBOARD-002', 38000, 40, 2,'ACTIVE', 'https://coolenjoy.net/data/editor/2310/627756db3d74209960e24fee0f1ffdf276266049.jpg', '레트로한 기계식 키보드', NOW()),
                                                                                                                                                      (1, 3, '베이직 양말 세트', 'SOCKS-001', 12000, 200, 5, 'ACTIVE', 'https://images.unsplash.com/photo-1586350977771-b3b0abd50c82?w=400', '사계절용 기본 양말 5켤레', NOW()),
                                                                                                                                                      (1, 4, '윈드브레이커', 'JACKET-001', 55000, 20, 14, 'ACTIVE', 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400', '가벼운 바람막이 자켓', NOW()),
                                                                                                                                                      (1, 3, '베이직 볼캡', 'CAP-001', 27000, 60, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1588850561407-ed78c282e89b?w=400', '심플한 디자인의 볼캡', NOW()),
                                                                                                                                                      (1, 3, '캔버스 에코백', 'BAG-001', 22000, 80, 35, 'ACTIVE', 'https://images.unsplash.com/photo-1590874103328-eac38a683ce7?w=400', '데일리용 에코백', NOW()),
                                                                                                                                                      (2, 1, '나시 티셔츠', 'SLEEVELESS-001', 18000, 0, 0, 'SOLD_OUT', 'https://images.unsplash.com/photo-1622445275576-721325763afe?w=400', '여름용 민소매 티셔츠', NOW()),
                                                                                                                                                      (2, 1, '울 가디건', 'CARDIGAN-001', 49000, 25, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1434389677669-e08b4cac3105?w=400', '따뜻한 울 혼방 가디건', NOW()),
                                                                                                                                                      (2, 1, '체크 셔츠', 'SHIRT-001', 35000, 45, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=400', '클래식한 체크 패턴 셔츠', NOW()),
                                                                                                                                                      (2, 2, '가죽 가방', 'SLACKS-001', 42000, 35, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1594633313593-bab3825d0caf?w=400', '트렌디한 가죽 가방', NOW()),
                                                                                                                                                      (2, 1, '트렌치 코트', 'COAT-001', 120000, 10, 0, 'ACTIVE', 'https://images.unsplash.com/photo-1539533018447-63fcce2678e3?w=400', '봄 가을용 클래식 트렌치 코트', NOW()),
                                                                                                                                                      (2, 1, '그래픽 티셔츠', 'TSHIRT-002', 22000, 70, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1583743814966-8936f5b7be1a?w=400', '스트릿 감성의 그래픽 티셔츠', NOW()),
                                                                                                                                                      (2, 2, '조거 팬츠', 'PANTS-002', 33000, 60, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1555689502-c4b22d76c56f?w=400', '활동성이 좋은 조거 트레이닝 바지', NOW()),
                                                                                                                                                      (2, 1, '터틀넥 니트', 'KNIT-001', 38000, 20, 10, 'ACTIVE', 'https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=400', '부드러운 촉감의 터틀넥 니트', NOW()),
                                                                                                                                                      (2, 1, '레더 자켓', 'JACKET-002', 95000, 12, 2, 'ACTIVE', 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=400', '세련된 인조 가죽 자켓', NOW()),
                                                                                                                                                      (2, 3, '티셔츠 여러장', 'BEANIE-001', 15000, 100, 20, 'ACTIVE', 'https://images.unsplash.com/photo-1576871337622-98d48d1cf531?w=400', '여름철 필수 검은색 티셔츠', NOW());

