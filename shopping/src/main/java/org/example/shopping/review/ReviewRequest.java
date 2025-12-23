package org.example.shopping.review;

import lombok.Data;
import org.example.shopping.users.User;
import org.springframework.web.multipart.MultipartFile;

public class ReviewRequest {

    @Data
    public static class SaveDTO {
        private int rating;
        private String content;
        private MultipartFile reviewImage;

        // 검증 메서드
        public void validate() {
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("평점은 1~5점만 가능합니다.");
            }

            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
        }

        public Review toEntity(User user, String reviewImageFileName) {
            return Review.builder()
                    .user(user)
                    .content(this.content)
                    .rating(this.rating)
                    .reviewImage(reviewImageFileName)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        private int rating;
        private String content;
        private MultipartFile reviewImage;
        private String profileImageFilename;

        // 검증 메서드
        public void validate() {
            if (rating < 1 || rating > 5) {
                throw new IllegalArgumentException("평점은 1~5점만 가능합니다.");
            }

            if (content == null || content.trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }

            // 파일 선택한 경우에만 검증
            if (reviewImage != null && !reviewImage.isEmpty()) {
                if (reviewImage.getContentType() == null ||
                        !reviewImage.getContentType().startsWith("image/")) {
                    throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
                }
            }
        }
    }
}
