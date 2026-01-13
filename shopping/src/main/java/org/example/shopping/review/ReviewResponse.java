package org.example.shopping.review;

import lombok.Data;
import org.example.shopping._core.utils.MyDateUtil;

import java.util.List;
import java.util.Stack;
import java.util.stream.IntStream;

public class ReviewResponse {

//    @Data
//    public static class RatingStatisticsDTO {
//        private Long firstPercentage;
//        private Long secondPercentage;
//        private Long thirdPercentage;
//        private Long forthPercentage;
//        private Long fifthPercentage;
//
//        public RatingStatisticsDTO(Review review) {
//            this.firstPercentage = getFirstPercentage();
//            this.secondPercentage = getSecondPercentage();
//            this.thirdPercentage = getThirdPercentage();
//            this.forthPercentage = getForthPercentage();
//            this.fifthPercentage = getFifthPercentage();
//        }
//    }

    @Data
    public static class InProductReviewDTO {
        private Long id;
        private String username;
        private int rating;
        private String reviewImage;
        private String content;

        public InProductReviewDTO(Review review) {
            this.id = review.getId();
            this.username = review.getUser().getUsername();
            this.rating = review.getRating();
            this.reviewImage = review.getReviewImage();
            this.content = review.getContent();
        }

        public String getStars() {
            return "★".repeat(this.rating) + "☆".repeat(5 - this.rating);
        }
    }

    @Data
    public static class ListDTO {
        private Long id;
        private String content;
        private String username;
        private String createdAt;
        private int rating;
        private List<Integer> stars;
        private List<Integer> emptyStars;

        private Long productId;
        private String productName;

        /** 상품 상세페이지 리뷰용 */
//        public ListDTO(Review review) {
////            this.productId = orderItem.getProduct().getId();
////            this.productName = orderItem.getProduct().getProductName();
//            this.id = review.getId();
//            this.content = review.getContent();
//
//            if (review.getUser() != null) {
//                this.username = review.getUser().getUsername();
//            }
//
//            if (review.getCreatedAt() != null) {
//                this.createdAt = MyDateUtil.toDateString(review.getCreatedAt());
//            }
//        }

        /** 마이페이지 리뷰용 */
        public ListDTO(Review review) {
            this.id = review.getId();
            this.content = review.getContent();
            this.rating = review.getRating();

            if (review.getUser() != null) {
                this.username = review.getUser().getUsername();
            }

            if (review.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.toDateString(review.getCreatedAt());
            }

            this.stars = IntStream.range(0, rating).boxed().toList(); // 0부터 rating (3) 0부터 3까지 반복해줘~
            this.emptyStars = IntStream.range(0, 5 - rating).boxed().toList();

        }
    } // end of static inner class

    @Data
    public static class DetailDTO {
        private String content;
        private Long userId;
        private String username;
        private String reviewImage;
        private String createdAt;

        private int rating;
        private List<Integer> stars;
        private List<Integer> emptyStars;

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

            this.stars = IntStream.range(0, rating).boxed().toList(); // 0부터 rating (3) 0부터 3까지 반복해줘~
            this.emptyStars = IntStream.range(0, 5 - rating).boxed().toList();
        }
    } // end of static inner class

    @Data
    public static class UpdateFormDTO {
        private String content;
        private int rating;
        private String reviewImage;

        private boolean checked1;
        private boolean checked2;
        private boolean checked3;
        private boolean checked4;
        private boolean checked5;

        public UpdateFormDTO(Review review) {
            this.content = review.getContent();
            this.rating = review.getRating();
            this.reviewImage = review.getReviewImage();

            this.checked1 = getRating() == 1;
            this.checked2 = getRating() == 2;
            this.checked3 = getRating() == 3;
            this.checked4 = getRating() == 4;
            this.checked5 = getRating() == 5;
        }


    }
}
