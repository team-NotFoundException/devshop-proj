package org.example.shopping.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.example.shopping.product.Product;
import org.example.shopping.users.User;
import org.springframework.web.multipart.MultipartFile;

public class ReviewRequest {

    @Data
    public static class SaveDTO {
        private Long productId;

        @Min(value = 1, message = "별점은 1점 이상이어야 합니다.")
        @Max(value = 5, message = "별점은 5점 이하여야 합니다.")
        private int rating;

        @NotBlank(message = "내용이 비워질 수 없습니다.")
        @Max(value = 150, message = "150자를 초과할 수 없습니다.")
        private String content;
        private MultipartFile reviewImage;


        public Review toEntity(User user, Product product, String reviewImageFileName) {
            return Review.builder()
                    .user(user)
                    .product(product)
                    .content(content)
                    .rating(rating)
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
