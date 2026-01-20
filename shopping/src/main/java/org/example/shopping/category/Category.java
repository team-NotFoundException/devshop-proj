package org.example.shopping.category;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.utils.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
@Setter
@Table(name = "categories")
public class Category  extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    private int depth;

    private Long displayOrder;


    private String imageUrl;

    @Builder
    public Category(String categoryName, int depth, Long displayOrder, String imageUrl, Category parent) {
        this.categoryName = categoryName;
        this.depth = depth;
        this.displayOrder = displayOrder;
        this.imageUrl = imageUrl;
        this.parent = parent;
    }

    public void setCategoryName(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new Exception400("카테고리 제목은 필수 입니다");
        }
        this.categoryName = categoryName;
    }

    public void setDisplayOrder(Long displayOrder) {
        if (displayOrder < 0) {
            throw new Exception400("정렬은 반드시 0이 필수 입니다.");
        }
        this.displayOrder = displayOrder;
    }

}