package org.example.shopping.review;

import lombok.Data;

import java.time.LocalDateTime;

public class ReviewResponse {

    @Data
    public static class ListDTO {
        private String content;
        private String username;
        private LocalDateTime createdAt;

        public ListDTO(Review review) {
            this.content = review.getReview();

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
        private String photoUrl;
        private LocalDateTime createdAt;

        public DetailDTO(Review review) {
            this.content = review.getReview();

            if (review.getUser() != null) {
                this.username = review.getUser().getUsername();
            }

            this.rating = review.getRating();
            this.photoUrl = review.getPhotoUrl();

            if (review.getCreatedAt() != null) {
                this.createdAt = review.getCreatedAt();
            }
        }
    } // end of static inner class

    @Data
    public static class UpdateFormDTO {
        private String review;
        private int rating;
        private String photoUrl;

        public UpdateFormDTO(Review review) {
            this.review = getReview();
            this.rating = review.getRating();
            this.photoUrl = review.getPhotoUrl();
        }
    }
}
