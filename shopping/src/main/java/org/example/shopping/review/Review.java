package org.example.shopping.review;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shopping.orderItem.OrderItem;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 작성자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 조회 편의용 (상품상세에서 필요)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String content;

    private int rating;

    private String reviewImage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Review(User user, int rating, String content, String reviewImage) {
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.reviewImage = reviewImage;
    }

    public void update(ReviewRequest.UpdateDTO updateDTO) {
        updateDTO.validate();

        this.rating = updateDTO.getRating();
        this.content = updateDTO.getContent();

        // 기존 이미지가 있다면 그대로 유지
        if (updateDTO.getProfileImageFilename() != null &&
                !updateDTO.getProfileImageFilename().isBlank()) {
            this.reviewImage = updateDTO.getProfileImageFilename();
        }
    }

    // 리뷰 정보 소유자 확인 로직
    public boolean isOwner(Long userId) {
        return this.user.getId().equals(userId);
    }
}