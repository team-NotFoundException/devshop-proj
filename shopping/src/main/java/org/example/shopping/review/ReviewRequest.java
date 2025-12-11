package org.example.shopping.review;

import lombok.Data;

public class ReviewRequest {

    @Data
    public static class SaveDTO {
//        private String productId;
//        private String userId;
        private String content;
        private int rating;
        private String photoUrl;

        public Review toEntity() {
            return new Review(
//                    productId,
//                    userId,
                    rating,
                    content,
                    photoUrl
            );
        }
    }

    @Data
    public static class UpdateDTO {
        private int rating;
        private String content;
        private String photoUrl;

        // 검증 메서드
        public void validate() {
            if (rating == 0) {
                throw new IllegalArgumentException("1~5 점을 선택하세요.");
            }

            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
        }
    }
}
