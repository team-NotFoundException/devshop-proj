package org.example.shopping.review;

import lombok.Data;

public class ReviewRequest {

    @Data
    public static class SaveDTO {
        private String productId;
        private String userId;
        private String content;
        private int rating;
        private String photoUrl;

        public Review toEntity() {
            return new Review(
                    productId,
                    userId,
                    rating,
                    content,
                    photoUrl
            );
        }
    }

    @Data
    public static class updateDTO {

    }
}
