package org.example.shopping.review;

import lombok.Data;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.orderItem.OrderItem;

public class ReviewResponse {

    @Data
    public static class ListDTO {
        private Long id;
        private String content;
        private String username;
        private String createdAt;

        private Long productId;
        private String productName;

        public ListDTO(Review review, OrderItem orderItem) {
//            this.productId = orderItem.getProduct().getId();
//            this.productName = orderItem.getProduct().getProductName();
            this.id = review.getId();
            this.content = review.getContent();

            if (review.getUser() != null) {
                this.username = review.getUser().getUsername();
            }

            if (review.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.toDateString(review.getCreatedAt());
            }
        }
    } // end of static inner class

    @Data
    public static class DetailDTO {
        private String content;
        private Long userId;
        private String username;
        private int rating;
        private String reviewImage;
        private String createdAt;

        public DetailDTO(Review review) {
            this.content = review.getContent();

            if (review.getUser() != null) {
                this.userId = review.getUser().getId();
                this.username = review.getUser().getUsername();
            }

            this.rating = review.getRating();
            this.reviewImage = review.getReviewImage();

            if (review.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.toDateString(review.getCreatedAt());
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
