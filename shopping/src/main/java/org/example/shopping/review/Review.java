package org.example.shopping.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "reviews_tb")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productId;

    private String userId;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    private String content;

    private int rating;

    private String photoUrl;

    public Review(String productId, String userId, int rating, String content, String photoUrl) {
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
        this.photoUrl = photoUrl;
    }
}
