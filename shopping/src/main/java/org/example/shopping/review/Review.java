package org.example.shopping.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

//    private String productId;
//
//    private String userId;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String content;

    private int rating;

    private String photoUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public Review(int rating, String content, String photoUrl) {
//        this.productId = productId;
//        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.photoUrl = photoUrl;
    }

    public void update(ReviewRequest.UpdateDTO updateDTO) {
        updateDTO.validate();

        this.rating = updateDTO.getRating();
        this.content = updateDTO.getContent();
        this.photoUrl = updateDTO.getPhotoUrl();
    }
}
