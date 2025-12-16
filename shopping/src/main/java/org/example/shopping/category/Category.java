package org.example.shopping.category;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    private int depth;
    private int displayOrder;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Category(String categoryName, int depth, int displayOrder, Category parent) {
        this.categoryName = categoryName;
        this.depth = depth;
        this.displayOrder = displayOrder;
        this.parent = parent;
    }

    public void setCategoryName(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("카테고리 제목은 필수 입니다");
        }
        this.categoryName = categoryName;
    }

    public void setDisplayOrder(int displayOrder) {
        if (displayOrder < 0) {
            throw new IllegalArgumentException("정렬은 반드시 0이 필수 입니다.");
        }
        this.displayOrder = displayOrder;
    }
}