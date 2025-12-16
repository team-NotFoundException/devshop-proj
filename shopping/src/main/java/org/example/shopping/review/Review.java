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
@Table(name = "reviews_tb")
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
    private String review;

    private int rating;

    private String photoUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Review(Product product, User user, int rating, String review, String photoUrl) {
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.review = review;
        this.photoUrl = photoUrl;
    }

    public void update(ReviewRequest.UpdateDTO updateDTO) {
        updateDTO.validate();

        this.rating = updateDTO.getRating();
        this.review = updateDTO.getReview();
        this.photoUrl = updateDTO.getPhotoUrl();
    }

    // 리뷰 정보 소유자 확인 로직
    public boolean isOwner(Long userId) {
        return this.user.getId().equals(userId);
    }
}