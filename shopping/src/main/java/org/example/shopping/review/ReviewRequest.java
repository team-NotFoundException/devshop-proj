package org.example.shopping.review;

import lombok.Data;
import org.example.shopping.user.User;

public class ReviewRequest {

    @Data
    public static class SaveDTO {
//        private String productId;
        private int rating;
        private String content;
        private User user;
        private String photoUrl;

        public Review toEntity(User user) {
            return new Review(
//                    productId,
                    rating,
                    content,
                    user,
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
