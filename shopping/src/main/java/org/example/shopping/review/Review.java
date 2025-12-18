package org.example.shopping.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shopping.product.Product;
import org.example.shopping.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_tb")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String content;

    private int rating;

    private String reviewImage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Review(User user, int rating, String content, String reviewImage) {
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.reviewImage = reviewImage;
    }
    /** Product 포함*/
//    public Review(Product product, User user, int rating, String review, String photoUrl) {
//        this.product = product;
//        this.user = user;
//        this.rating = rating;
//        this.review = review;
//        this.photoUrl = photoUrl;
//    }

    public void update(ReviewRequest.UpdateDTO updateDTO) {
        updateDTO.validate();

        this.rating = updateDTO.getRating();
        this.content = updateDTO.getContent();
        this.reviewImage = updateDTO.getReviewImage();
    }

    // 리뷰 정보 소유자 확인 로직
    public boolean isOwner(Long userId) {
        return this.user.getId().equals(userId);
    }
}