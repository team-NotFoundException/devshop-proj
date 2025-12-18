package org.example.shopping.review;

import lombok.Data;
import org.example.shopping.product.Product;
import org.example.shopping.user.User;

public class ReviewRequest {

    @Data
    public static class SaveDTO {
        private int rating;
        private String content;
        private String reviewImage;

        // 검증 메서드
        public void validate() {
            if (rating < 1 || rating >5) {
                throw new IllegalArgumentException("평점은 1~5점만 가능합니다.");
            }

            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
        }

        public Review toEntity(User user) {
            return new Review(
                    user,
                    rating,
                    content,
                    reviewImage
            );
        }
//        public Review toEntity(User user, Product product) {
//            return new Review(
//                    product,
//                    user,
//                    rating,
//                    review,
//                    reviewImage
//            );
//        }
    }

    @Data
    public static class UpdateDTO {
        private int rating;
        private String content;
        private String reviewImage;

        // 검증 메서드
        public void validate() {
            if (rating < 1 || rating >5) {
                throw new IllegalArgumentException("평점은 1~5점만 가능합니다.");
            }

            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
        }
    }
}
