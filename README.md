# devshop-proj

# 개발자 용품 쇼핑몰 시스템

## 프로젝트 개요

개발자 용품 쇼핑몰 시스템은 개발자가 개발에 필요한 용품(키보드, 마우스, 모니터, 의자 등)을 구매할 수 있는 웹 기반 쇼핑몰 플랫폼입니다. 사용자(User), 판매자(Owner), 관리자(Admin)의 역할 기반 접근 제어를 통해 각 사용자 그룹에 맞는 기능을 제공하며, 상품 관리부터 주문, 결제, 리뷰에 이르기까지 쇼핑몰 운영의 전 과정을 하나의 서비스로 통합한 웹 애플리케이션입니다.

### 해결하고자 하는 문제

기존 쇼핑몰 시스템들은 다음과 같은 문제점이 있습니다:

- 복잡한 권한 관리로 인한 접근 제어 어려움
- 판매자와 관리자의 기능 혼재로 인한 관리 비효율
- 결제 시스템의 확장성 부족
- 파일 업로드 및 관리의 비효율성
- RESTful API 설계 미흡으로 인한 유지보수 어려움

### 제공하는 가치

- **사용자**: 간편한 상품 조회, 장바구니 관리, 안전한 결제 경험 제공
- **판매자**: 상품 등록 및 관리, 판매 통계 확인을 통한 효율적인 판매 관리
- **관리자**: 사용자 관리, 판매자 승인, 카테고리 관리를 통한 플랫폼 운영

## 기술 스택

### Backend
- **Framework**: Spring Boot 3.5.8
- **Language**: Java 17
- **ORM**: Spring Data JPA
- **Database**: MySQL 8.0, H2 (개발 환경)
- **Template Engine**: Mustache
- **Security**: Spring Security Crypto (BCrypt)
- **Validation**: Jakarta Bean Validation
- **Build Tool**: Gradle

### Frontend
- **Template**: Mustache
- **CSS**: Custom CSS
- **JavaScript**: Vanilla JavaScript

### External API
- **결제**: Toss Payments API
- **소셜 로그인**: 카카오 OAuth 2.0
- **주소 검색**: 주소 API (Juso API)
- **양방향 실시간 통신**: WebSocket API + STOMP
- **스마트 에디터**: toast API
- **이메일 인증**: Google SMTP

### Development Tools
- **IDE**: IntelliJ IDEA
- **Version Control**: Git
- **Database Console**: H2 Console (개발 환경)
- **DBMS**: MySql

## 주요 기능

### 1. 사용자 관리
- 회원가입 및 로그인/로그아웃
- 카카오 소셜 로그인 연동
- Google SMTP 활용 이메일 인증
- 회원정보 조회 및 수정
- 아이디 중복 체크
- 주소 검색 API 연동
- 비밀번호 암호화 (BCrypt)
- 권한 기반 접근 제어 (USER, OWNER, ADMIN)

### 2. 상품 관리
- 상품 등록, 조회, 수정, 삭제 (판매자 권한)
- 상품 이미지 업로드 및 관리
- 상품 상태 관리 (ACTIVE, SOLD_OUT 등)
- 카테고리별 상품 조회
- 상품 상세 정보 조회
- 상품별 매출 통계

### 3. 카테고리 관리
- 계층형 카테고리 구조 (상위/하위 카테고리)
- 카테고리 등록, 조회, 수정, 삭제 (관리자 권한)
- 카테고리 네비게이션 제공
- 카테고리별 상품 분류

### 4. 장바구니 관리
- 장바구니 생성 (회원가입 시 자동 생성)
- 상품 추가 및 삭제
- 상품 선택/해제 기능
- 전체 선택/해제 기능
- 수량 및 옵션 변경
- 장바구니 총액 계산
- 선택된 상품 일괄 삭제

### 5. 주문 관리
- 주문 생성 (장바구니에서 선택된 상품 주문)
- 주문 목록 조회
- 주문 상세 조회
- 주문 검색 기능 (키워드 기반)

### 6. 결제 시스템
- Toss Payments API 연동
- Mock 결제 (테스트용)
- 결제 준비 및 승인 프로세스
- 결제 실패 처리
- 환불 처리
- PaymentGateway 패턴 적용 (확장성 고려)
- 재고 차감 로직

### 7. 리뷰 시스템
- 리뷰 작성, 조회, 수정, 삭제
- 리뷰 이미지 업로드 및 삭제
- 상품별 리뷰 목록 조회
- 내 리뷰 목록 조회
- 상품별 리뷰 평점 통계

### 8. 판매자 기능
- 판매자 회원가입
- 판매자 대시보드 (상품 통계)
- 판매자 정보 수정
- 판매자 상태 관리 (승인대기/승인/정지)
- 판매자 - 관리자 간 1:1 채팅 기능

### 9. 관리자 기능
- 사용자 목록 조회 및 관리
- 판매자 승인/거부/정지 처리
- 판매자 상태 관리
- 카테고리 작성, 조회, 수정, 삭제
- 최근 가입 사용자 조회
- 전체 상품에 대한 매출 통계 조회
- 관리자 - 판매자 간 1:1 채팅 기능

## 프로젝트 구조

```
shopping/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/shopping/
│   │   │       ├── _core/              # 공통 기능
│   │   │       │   ├── config/        # 설정 (WebMvcConfig, RestTemplateConfig)
│   │   │       │   ├── errors/        # 예외 처리 (MyExceptionHandler, Exception400~500)
│   │   │       │   ├── interceptor/   # 인터셉터 (LoginInterceptor, OwnerInterceptor 등)
│   │   │       │   ├── fileupload/    # 파일 업로드 (ImageUploadController)
│   │   │       │   ├── infra/         # 외부 API 클라이언트 (KakaoOAuthClient)
│   │   │       │   ├── paging/        # 페이징 (PageRequestDTO, PageResponseDTO)
│   │   │       │   └── utils/         # 유틸리티 (FileUtil, ValidationUtils 등)
│   │   │       ├── cart/              # 장바구니 관리
│   │   │       ├── cartItem/          # 장바구니 항목
│   │   │       ├── category/          # 카테고리 관리
│   │   │       ├── order/             # 주문 관리
│   │   │       ├── payment/           # 결제
│   │   │       │   ├── dto/           # 결제 DTO
│   │   │       │   ├── error/         # 결제 예외 처리
│   │   │       │   ├── log/           # 결제 History 처리
│   │   │       │   ├── paymentEnum/   # 결제 관련 Enum
│   │   │       │   └── service/      # 결제 서비스 (PaymentGateway 패턴)
│   │   │       ├── product/           # 상품 관리
│   │   │       ├── review/            # 리뷰
│   │   │       ├── users/             # 사용자 관리
│   │   │       │   ├── user/         # 일반 사용자
│   │   │       │   ├── owner/        # 판매자
│   │   │       │   ├── admin/        # 관리자
│   │   │       │   ├── dto/          # 사용자 DTO
│   │   │       │   └── enums/        # 사용자 관련 Enum
│   │   │       └── ShoppingApplication.java
│   │   └── resources/
│   │       ├── templates/              # Mustache 템플릿
│   │       ├── static/                # 정적 리소스 (CSS, JS, 이미지)
│   │       ├── db/                    # 초기 데이터 (data.sql)
│   │       └── application*.yml       # 설정 파일
│   └── test/                          # 테스트 코드
├── build.gradle                       # Gradle 빌드 설정
├── gradlew                            # Gradle Wrapper (Linux/Mac)
└── gradlew.bat                        # Gradle Wrapper (Windows)
```

## 실행 방법

### 사전 요구사항
- JDK 17 이상
- MySQL 8.0 이상 (운영 환경) 또는 H2 (개발 환경)
- Gradle 7.x 이상

### 환경 변수 설정

프로젝트 루트에 `application-secret.yml` 파일을 생성하고 다음 설정을 추가하세요:

```yaml
oauth:
  kakao:
    client-id: your_kakao_client_key
    client-secret: your_kakao_secret_client_key

tenco:
  key: your_tenco_key

payment:
  toss:
    client-key: your_toss_client_key
    secret-key: your_toss_secret_key
    base-url: https://api.tosspayments.com

address:
  juso:
    key: your_juso_api_key

database:
	username: your_db_username
	password: your_db_password

file:
  upload-patch: C:/shopImages/

mail:
  password: your_google_smtp_mail_passowrd
```

### 데이터베이스 설정

#### 개발 환경 (H2)
- H2 데이터베이스 사용 (인메모리 모드)
- H2 Console 활성화: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:test;MODE=MySQL`
- `application-local.yml`에서 H2 설정 사용

#### 운영 환경 (MySQL)
- MySQL 8.0 이상 필요
- 데이터베이스 생성:
```sql
CREATE DATABASE shopping CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
- `application-dev.yml`에서 MySQL 설정 사용

### 빌드 및 실행

#### Windows 환경
```bash
# 프로젝트 클론
git clone [repository-url]
cd devshop-proj/shopping

# Gradle 빌드
gradlew.bat build

# 애플리케이션 실행
gradlew.bat bootRun

# 또는 JAR 파일로 실행
java -jar build/libs/shopping-0.0.1-SNAPSHOT.jar
```

#### Linux/Mac 환경
```bash
# 프로젝트 클론
git clone [repository-url]
cd devshop-proj/shopping

# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun

# 또는 JAR 파일로 실행
java -jar build/libs/shopping-0.0.1-SNAPSHOT.jar
```

### 접속 정보

- **애플리케이션**: http://localhost:8080
- **H2 Console** (개발 환경): http://localhost:8080/h2-console

## URL 매핑

### 홈/메인

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/` | 메인 페이지 (상품 목록) | 비로그인 | |

### 사용자 인증

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/user/login` | 로그인 화면 | 비로그인 | |
| POST | `/user/login` | 로그인 처리 | 비로그인 | |
| GET | `/user/logout` | 로그아웃 | 로그인 필요 | 수정 요망: DELETE `/sessions` 또는 POST `/auth/logout` |
| GET | `/user/join` | 회원가입 화면 | 비로그인 | |
| POST | `/user/join` | 회원가입 처리 | 비로그인 | |
| GET | `/kakao` | 카카오 로그인 콜백 | 비로그인 | |

### 사용자 정보

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/user/update` | 회원정보 수정 화면 | 로그인 필요 | 수정 요망: GET `/me` |
| POST | `/user/update` | 회원정보 수정 처리 | 로그인 필요 | 수정 요망: PUT `/me` 또는 PATCH `/me` |

### 상품

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/products/{productId}` | 상품 상세 조회 (사용자) | 비로그인 | |
| GET | `/category/{categoryId}` | 카테고리별 상품 목록 | 비로그인 | |
| GET | `/owner/products` | 상품 목록 (판매자) | 판매자 | |
| GET | `/owner/products/status/{status}` | 상태별 상품 목록 (판매자) | 판매자 | |
| GET | `/owner/products/category/{categoryId}` | 카테고리별 상품 목록 (판매자) | 판매자 | |
| GET | `/owner/products/save` | 상품 등록 화면 | 판매자 | 수정 요망: GET `/owner/products` (생성 폼은 선택적) |
| POST | `/owner/products/save` | 상품 등록 처리 | 판매자 | 수정 요망: POST `/owner/products` |
| GET | `/owner/products/{id}/edit` | 상품 수정 화면 | 판매자 | 수정 요망: GET `/owner/products/{id}` (수정 폼은 선택적) |
| POST | `/owner/products/{id}/edit` | 상품 수정 처리 | 판매자 | 수정 요망: PUT `/owner/products/{id}` 또는 PATCH `/owner/products/{id}` |
| POST | `/owner/products/{id}/delete` | 상품 삭제 | 판매자 | 수정 요망: DELETE `/owner/products/{id}` |
| GET | `/owner/products/{id}` | 상품 상세 조회 (판매자) | 판매자 | |

### 카테고리

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/admin/categories/save` | 카테고리 등록 화면 | 관리자 | 수정 요망: GET `/admin/categories` (생성 폼은 선택적) |
| POST | `/admin/categories/child-save` | 하위 카테고리 등록 처리 | 관리자 | 수정 요망: POST `/admin/categories` (body에 type 구분) |
| POST | `/admin/categories/parent-save` | 상위 카테고리 등록 처리 | 관리자 | 수정 요망: POST `/admin/categories` (body에 type 구분) |
| GET | `/admin/categories/list` | 카테고리 목록 조회 | 관리자 | 수정 요망: GET `/admin/categories` (/list 제거) |
| GET | `/categories/nav` | 카테고리 네비게이션 | 비로그인 | |
| GET | `/admin/categories/{id}/edit` | 카테고리 수정 화면 | 관리자 | |
| POST | `/admin/categories/{id}/edit` | 카테고리 수정 | 관리자 | |
| POST | `/admin/categories/{id}/delete` | 카테고리 삭제 | 관리자 | |

### 장바구니

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/cart/list` | 장바구니 목록 조회 | 로그인 필요 | |
| POST | `/cart/add` | 장바구니에 상품 추가 | 로그인 필요 | 수정 요망: POST `/cart/items` |
| POST | `/cart/delete-checked` | 선택된 항목 일괄 삭제 | 로그인 필요 | 수정 요망: DELETE `/cart/items` (body에 선택된 항목) |
| POST | `/cart/{cartItemId}/delete` | 장바구니 항목 삭제 | 로그인 필요 | 수정 요망: DELETE `/cart/items/{cartItemId}` |
| POST | `/cart/{cartItemId}/update-check` | 장바구니 항목 선택 상태 변경 | 로그인 필요 | 수정 요망: PATCH `/cart/items/{cartItemId}` (body에 checked 상태) |
| POST | `/cart/toggle-all` | 전체 선택/해제 | 로그인 필요 | 수정 요망: PATCH `/cart` (body에 전체 선택 상태) |
| POST | `/cart/{cartItemId}/update-option` | 장바구니 항목 옵션 변경 | 로그인 필요 | 수정 요망: PATCH `/cart/items/{cartItemId}` (body에 옵션 정보) |

### 리뷰

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/review/{productId}/save` | 리뷰 작성 화면 | 로그인 필요 | |
| POST | `/review/{productId}/save` | 리뷰 작성 처리 | 로그인 필요 | |
| GET | `/products/{productId}/review` | 상품별 리뷰 목록 조회 | 비로그인 | |
| GET | `/products/{productId}/statistics` | 상품별 리뷰 통계 조회 | 비로그인 | |
| GET | `/review/list` | 내 리뷰 목록 조회 | 로그인 필요 | |
| GET | `/review/{reviewId}` | 리뷰 상세 조회 | 로그인 필요 | |
| GET | `/review/{reviewId}/update` | 리뷰 수정 화면 | 로그인 필요 | |
| POST | `/review/{reviewId}/update` | 리뷰 수정 처리 | 로그인 필요 | 수정 요망: PUT `/review/{reviewId}` 또는 PATCH `/review/{reviewId}` |
| POST | `/review/{reviewId}/delete` | 리뷰 삭제 | 로그인 필요 | 수정 요망: DELETE `/review/{reviewId}` |
| POST | `/review/{reviewId}/review-image/delete` | 리뷰 이미지 삭제 | 로그인 필요 | 수정 요망: DELETE `/review/{reviewId}/images` |

### 주문

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/order/list` | 주문 목록 조회 | 로그인 필요 | |
| GET | `/order/detail` | 주문 상세 조회 | 로그인 필요 | |

### 결제

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/payment` | 결제 화면 | 로그인 필요 | |
| POST | `/payment/cart/{cartId}` | 결제 생성 (Mock) | 로그인 필요 | |
| GET | `/payment/cart/{cartId}/approve` | 결제 승인 처리 | 로그인 필요 | 수정 요망: POST `/payment/cart/{cartId}/approve` (GET → POST) => 수정 시 로직 흐름 꼬임 |
| GET | `/payment/cart/{cartId}/fail` | 결제 실패 화면 | 로그인 필요 | |
| POST | `/payment/{id}/refund` | 환불 처리 | 로그인 필요 | |

### 관리자

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/admin/main` | 관리자 대시보드 | 관리자 | |
| GET | `/admin/list` | 사용자 목록 조회 | 관리자 | |
| POST | `/admin/user/{userId}/delete` | 사용자 삭제 | 관리자 | 수정 요망: DELETE `/admin/users/{userId}` |
| GET | `/admin/owner/list` | 판매자 목록 조회 | 관리자 | |
| POST | `/admin/owner/{ownerId}/approve` | 판매자 승인 | 관리자 | 수정 요망: PATCH `/admin/owners/{ownerId}` (body에 status 변경) |
| POST | `/admin/owner/{ownerId}/suspension` | 판매자 정지 | 관리자 | 수정 요망: PATCH `/admin/owners/{ownerId}` (body에 status 변경) |
| GET | `/admin/owner/{ownerId}/detail` | 판매자 상세 조회 | 관리자 | |
| GET | `/admin/chat/list` | 채팅방 목록 조회 | 관리자 | |
| GET | `/admin/chat/{chatRoomId}` | 특정한 채팅방 입장 | 관리자 | |
| GET | `/admin/statistics` | 통합 매출 통계 화면 | 관리자 | |


### 판매자

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/owner/join` | 판매자 회원가입 화면 | 비로그인 | |
| POST | `/owner/join` | 판매자 회원가입 처리 | 비로그인 | |
| GET | `/owner/logout` | 판매자 로그아웃 | 판매자 | 수정 요망: DELETE `/sessions` 또는 POST `/auth/logout` |
| GET | `/owner/update` | 판매자 정보 수정 화면 | 판매자 | 수정 요망: GET `/owner/me` |
| POST | `/owner/update` | 판매자 정보 수정 처리 | 판매자 | 수정 요망: PUT `/owner/me` 또는 PATCH `/owner/me` |
| GET | `/owner/dashboard` | 판매자 대시보드 | 판매자 | |
| GET | `/owner/chatRoom/me` | 판매자 채팅 생성 | 판매자 | |
| POST | `/chatRoom/delete` | 1:1 채팅방 삭제 | 판매자 | |

### 기타

| HTTP Method | URL | 설명 | 권한 | 비고 |
|------------|-----|------|------|------|
| GET | `/popup/juso` | 주소 검색 팝업 | 비로그인 | |
| POST | `/popup/juso` | 주소 검색 처리 | 비로그인 | |
| POST | `user/email/send` | 인증 이메일 발송 처리 | 비로그인 | |
| POST | `user/email/verify` | 인증 번호 검증 처리 | 비로그인 | |
| GET | `/test` | 테스트 페이지 | 비로그인 | |
| POST | `/api/upload/editor-image` | 이미지 업로드 (에디터용) | 로그인 필요 | |

## 주요 기술 특징

### 보안
- BCrypt를 이용한 비밀번호 암호화
- 세션 기반 인증
- 인터셉터를 통한 접근 제어
- 권한별 기능 분리 (USER, OWNER, ADMIN)

### 예외 처리
- 커스텀 예외 클래스 (Exception400, 401, 403, 404, 500)
- GlobalExceptionHandler를 통한 전역 예외 처리
- 사용자 친화적 에러 메시지 제공
- 결제 시스템용 BusinessException 및 ErrorCode 패턴 적용

### 데이터베이스
- JPA를 통한 ORM
- 트랜잭션 관리 (@Transactional)
- 연관관계 매핑 (OneToOne, ManyToOne, OneToMany)
- BaseTimeEntity를 통한 공통 필드 관리

### 파일 업로드
- 이미지 파일 업로드 지원 (상품 이미지, 리뷰 이미지)
- 파일 유효성 검증 (이미지 파일만 허용)
- UUID 기반 파일명 생성
- 최대 파일 크기: 10MB
- 파일 저장 경로: `C:/shopImages/` (Windows 환경)
- 정적 리소스 핸들러 설정

### 인터셉터
- **LoginInterceptor**: 로그인 필수 페이지 보호
- **OwnerInterceptor**: 판매자 권한 검증
- **AdminInterceptor**: 관리자 권한 검증
- **SessionInterceptor**: 세션 정보 전역 제공
- **CategoryInterceptor**: 카테고리 정보 전역 제공

### 결제 시스템
- PaymentGateway 패턴 적용 (확장성 고려)
- Toss Payments API 연동
- Mock 결제 지원 (테스트용)
- 결제 승인 및 환불 처리
- 결제 상태 관리

### 검증 및 유틸리티
- Jakarta Bean Validation 활용
- ValidationGroups를 통한 그룹별 검증
- ValidationUtils를 통한 중앙화된 검증 처리
- MoneyUtils, MyDateUtil 등 유틸리티 클래스 제공

## 개발 환경 설정

### IDE 설정
- IntelliJ IDEA 권장
- Lombok 플러그인 설치 필요
- Java 17 SDK 설정

### 데이터베이스 설정

#### 개발 환경 (H2)
- H2 데이터베이스 사용 (인메모리 모드)
- H2 Console 활성화: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:test;MODE=MySQL`
- `application-local.yml`에서 H2 설정 사용

#### 운영 환경 (MySQL)
- MySQL 8.0 이상 필요
- 데이터베이스 생성:
```sql
CREATE DATABASE shopping CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```
- `application-dev.yml`에서 MySQL 설정 사용

### 프로필 설정

애플리케이션 실행 시 프로필을 선택할 수 있습니다:

- **local**: H2 데이터베이스 사용 (개발 환경)
- **dev**: MySQL 데이터베이스 사용 (개발 환경)
- **prod**: MySQL 데이터베이스 사용 (운영 환경)

`application.yml`에서 활성 프로필을 설정합니다.

## 프로젝트를 통해 얻은 경험

- Spring Boot MVC 구조 설계 및 구현
- JPA 연관관계 및 ERD 설계
- 권한 분리 및 인증/인가 처리
- 파일 업로드 및 관리 시스템 구현
- 쇼핑몰 핵심 기능 구현 경험
- 결제 시스템 연동 (Toss Payments)
- 소셜 로그인 연동 (카카오 OAuth)
- 인터셉터를 통한 접근 제어 구현
- 예외 처리 및 에러 핸들링
- RESTful API 설계 고려사항 학습
- PaymentGateway 패턴을 통한 확장 가능한 아키텍처 설계


## 향후 개선 사항

- JWT 기반 인증 방식 도입 
- 관리자 대시보드 고도화    
- 상품 검색 및 필터링    
- 결제 시스템 고도화     
- 테스트 코드 작성
- Naver 로그인 도입
- 

---

## 한 줄 요약

> **Spring Boot와 Mustache를 활용한
> 권한 기반 개발자 용품 쇼핑몰 웹 애플리케이션**




---

<div align="center">

<br/>

![Java](https://img.shields.io/badge/Java-17-007396?style=flat-square\&logo=java\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=flat-square\&logo=springboot\&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-Hibernate-59666C?style=flat-square)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=flat-square\&logo=mysql\&logoColor=white)
![Mustache](https://img.shields.io/badge/Mustache-Template-000000?style=flat-square)
![Git](https://img.shields.io/badge/Git-Version_Control-F05032?style=flat-square\&logo=git\&logoColor=white)

</div>

---


<h3 align="center">🏅 Stats</h3>

<table width="100%" align = "center">
<tr>
  <th>팀원</th>
  <th>GitHub Stats</th>
  <th>Top Languages</th>
</tr>

<tr>
  <td align="center">
    <a href="https://github.com/Kitbomin" target="_blank" >
      <img src="https://avatars.githubusercontent.com/u/127817110?v=4" width="100" border-radius="50%"/><br/>
      Kitbomin
    </a>
  </td>
  <td><img src="https://github-readme-stats.vercel.app/api?username=Kitbomin&custom_title=Kitbomin's%20Github%20Stat&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
  <td><img src="https://github-readme-stats.vercel.app/api/top-langs/?username=Kitbomin&layout=compact&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
</tr>

<tr>
  <td align="center">
    <a href="https://github.com/untilthe-end" target="_blank">
      <img src="https://avatars.githubusercontent.com/u/156649739?v=4" width="100" border-radius="50%"/><br/>
      untilthe-end
    </a>
  </td>
  <td><img src="https://github-readme-stats.vercel.app/api?username=untilthe-end&custom_title=untilthe-end's%20Github%20Stat&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
  <td><img src="https://github-readme-stats.vercel.app/api/top-langs/?username=untilthe-end&layout=compact&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
</tr>

<tr>
  <td align="center">
    <a href="https://github.com/ehdgn" target="_blank">
      <img src="https://avatars.githubusercontent.com/u/181971909?v=4" width="100" border-radius="50%"/><br/>
      ehdgn
    </a>
  </td>
  <td><img src="https://github-readme-stats.vercel.app/api?username=ehdgn&custom_title=ehdgn's%20Github%20Stat&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
  <td><img src="https://github-readme-stats.vercel.app/api/top-langs/?username=ehdgn&layout=compact&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
</tr>

<tr>
  <td align="center">
    <a href="https://github.com/kim-se-hoon" target="_blank">
      <img src="https://avatars.githubusercontent.com/u/219491357?v=4" width="100" border-radius="50%"><br/>
      kim-se-hoon
    </a>
  </td>
  <td><img src="https://github-readme-stats.vercel.app/api?username=kim-se-hoon&custom_title=kim-se-hoon's%20Github%20Stat&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
  <td><img src="https://github-readme-stats.vercel.app/api/top-langs/?username=kim-se-hoon&layout=compact&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
</tr>

<tr>
  <td align="center">
    <a href="https://github.com/jihoonjeong56" target="_blank">
      <img src="https://avatars.githubusercontent.com/u/100738560?v=4" width="100" border-radius="50%"/><br/>
      jihoonjeong56
    </a>
  </td>
  <td><img src="https://github-readme-stats.vercel.app/api?username=jihoonjeong56&custom_title=jihoonjeong56's%20Github%20Stat&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
  <td><img src="https://github-readme-stats.vercel.app/api/top-langs/?username=jihoonjeong56&layout=compact&bg_color=180,ffffff,&title_color=000000&text_color=000000" width="100%"/></td>
</tr>
</table>


---





```mermaid
erDiagram

    %% ============================
    %% SHIPPING INFO 추가
    %% ============================
    SHIPPING_INFO {
        BIGINT id PK
        BIGINT orderId FK
        VARCHAR receiverName
        VARCHAR receiverPhone
        VARCHAR address
        VARCHAR detailAddress
        VARCHAR requestMessage
    }

    ORDER ||--|| SHIPPING_INFO : "has shipping info"

    %% ============================
    %% USER & ROLE
    %% ============================
    USER {
        BIGINT id PK
        VARCHAR username
        VARCHAR password
        VARCHAR nickname
        VARCHAR email
        VARCHAR address
        VARCHAR phoneNum
        DATETIME createdAt
        DATETIME updatedAt
    }

    ROLE {
        BIGINT id PK
        VARCHAR roleName
    }

    USER_ROLES {
        BIGINT id PK
        BIGINT userId FK
        BIGINT roleId FK
    }

    USER ||--o{ USER_ROLES : "has"
    ROLE ||--o{ USER_ROLES : "assigned"

    %% ============================
    %% CATEGORY & PRODUCT
    %% ============================
    CATEGORY {
        BIGINT categoryId PK
        VARCHAR categoryName
        BIGINT parentId FK
        INT depth
        INT displayOrder
        DATETIME createdAt
    }

    PRODUCT {
        BIGINT productId PK
        BIGINT categoryId FK
        VARCHAR productName
        VARCHAR productCode
        DECIMAL price
        INT stockQuantity
        TEXT description
        VARCHAR thumbnailUrl
        ENUM status
        DATETIME createdAt
        DATETIME updatedAt
    }

    CATEGORY ||--o{ CATEGORY : "parent of"
    CATEGORY ||--o{ PRODUCT : "categorizes"

    %% ============================
    %% FILE MANAGEMENT
    %% ============================
    FILE_INFO {
        BIGINT fileId PK
        VARCHAR originalName
        VARCHAR storedName
        VARCHAR contentType
        BIGINT fileSize
        VARCHAR url
        DATETIME uploadedAt
    }

    PRODUCT_FILE {
        BIGINT id PK
        BIGINT productId FK
        BIGINT fileId FK
    }

    REVIEW_FILE {
        BIGINT id PK
        BIGINT reviewId FK
        BIGINT fileId FK
    }

    PRODUCT ||--o{ PRODUCT_FILE : "has image"
    FILE_INFO ||--o{ PRODUCT_FILE : "referenced by"

    REVIEW ||--o{ REVIEW_FILE : "has image"
    FILE_INFO ||--o{ REVIEW_FILE : "referenced by"

    %% ============================
    %% REVIEW
    %% ============================
    REVIEW {
        BIGINT reviewId PK
        BIGINT productId FK
        BIGINT userId FK
        INT rating
        TEXT content
        DATETIME createdAt
    }

    USER ||--o{ REVIEW : "writes"
    PRODUCT ||--o{ REVIEW : "has reviews"

    %% ============================
    %% CART
    %% ============================
    CART {
        BIGINT cartId PK
        BIGINT userId FK
    }

    CART_ITEM {
        BIGINT cartItemId PK
        BIGINT cartId FK
        BIGINT productId FK
        INT quantity
    }

    USER ||--|| CART : "owns"
    CART ||--o{ CART_ITEM : "contains"
    PRODUCT ||--o{ CART_ITEM : "in cart"

    %% ============================
    %% ORDER
    %% ============================
    "ORDER" {
        BIGINT orderId PK
        BIGINT userId FK
        ENUM orderStatus
        DECIMAL totalPrice
        BIGINT paymentId FK
        DATETIME createdAt
    }

    ORDER_ITEM {
        BIGINT orderItemId PK
        BIGINT orderId FK
        BIGINT productId FK
        INT quantity
        DECIMAL orderPrice
        DECIMAL totalPrice
    }

    USER ||--o{ ORDER : "places"
    ORDER ||--o{ ORDER_ITEM : "contains"
    PRODUCT ||--o{ ORDER_ITEM : "purchased"

    %% ============================
    %% PAYMENT
    %% ============================
    PAYMENT {
        BIGINT id PK
        VARCHAR username FK
        VARCHAR orderId
        VARCHAR paymentKey
        BIGINT amount
        ENUM method
        ENUM status
        VARCHAR productCode
        VARCHAR productName
        VARCHAR failureCode
        VARCHAR failureMessage
        DATETIME requestAt
        DATETIME approvedAt
        DATETIME cancelledAt
    }

    USER ||--o{ PAYMENT : "makes"
    ORDER ||--|| PAYMENT : "paid by"

```
