package org.example.shopping.product;

import jakarta.persistence.*;
import lombok.*;
import org.example.shopping.category.Category;
import org.example.shopping.product.productEnum.ProductStatus;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "product_tb")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private String productCode;
    private BigDecimal price;
    private int stockQuantity;
    private String description;
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    // N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Product(
            String productName,
            String productCode,
            BigDecimal price,
            int stockQuantity,
            String description,
            String thumbnailUrl,
            ProductStatus status,
            Category category
    ) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.status = status;
        this.category = category;
    }

    // 상품 수정
    public void update(ProductRequest.UpdateDTO dto, Category category) {
        dto.validate();
        this.productName = dto.getProductName();
        this.price = dto.getPrice();
        this.stockQuantity = dto.getStockQuantity();
        this.description = dto.getDescription();
        this.thumbnailUrl = dto.getThumbnailUrl();
        this.status = dto.getStatus();
        this.category = this.category;
    }

    // 재고 수정
    public void updateStock(int stockQuantity) {
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("재고는 0 이상이어야 합니다.");
        }
        this.stockQuantity = stockQuantity;
    }

    // 상태 변경
    public void updateStatus(ProductStatus status) {
        this.status = status;
    }
}