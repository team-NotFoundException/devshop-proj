package org.example.shopping.review;

import lombok.Data;
import org.example.shopping.product.Product;

import java.time.LocalDateTime;

public class ReviewResponse {

    @Data
    public static class ListDTO {
        private Long id;
        private String content;
        private String username;
        private LocalDateTime createdAt;

        public ListDTO(Review review) {
            this.id = review.getId();
            this.content = review.getContent();

            if (review.getUser() != null) {
                this.username = review.getUser().getUsername();
            }

            if (review.getCreatedAt() != null) {
                this.createdAt = review.getCreatedAt();
            }
        }
    } // end of static inner class

    @Data
    public static class DetailDTO {
        private String content;
        private String username;
        private int rating;
        private String reviewImage;
        private LocalDateTime createdAt;

        public DetailDTO(Review review) {
            this.content = review.getContent();

            if (review.getUser() != null) {
                this.username = review.getUser().getUsername();
            }

            this.rating = review.getRating();
            this.reviewImage = review.getReviewImage();

            if (review.getCreatedAt() != null) {
                this.createdAt = review.getCreatedAt();
            }
        }
    } // end of static inner class

    @Data
    public static class UpdateFormDTO {
        private String content;
        private int rating;
        private String reviewImage;

        public UpdateFormDTO(Review review) {
            this.content = review.getContent();
            this.rating = review.getRating();
            this.reviewImage = review.getReviewImage();
        }
    }
}
