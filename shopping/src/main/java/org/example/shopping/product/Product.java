package org.example.shopping.product;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.category.Category;
import org.example.shopping.product.productEnum.ProductStatus;
import org.example.shopping.users.owner.Owner;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "product_tb")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String productName;
    private String productCode;
    private Long price;
    private int stockQuantity;

    @Column(columnDefinition = "TEXT")
    private String thumbnailUrl;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String description;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    // N : 1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Product(Long id, Owner owner, String productName, String productCode, Long price, int stockQuantity, String thumbnailUrl, String description, ProductStatus status, Category category, Timestamp createdAt) {
        this.id = id;
        this.owner = owner;
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.thumbnailUrl = thumbnailUrl;
        this.description = description;
        this.status = status;
        this.category = category;
        this.createdAt = createdAt;
    }

//    @Builder
//    public Product(
//            String productName,
//            String productCode,
//            Long price,
//            int stockQuantity,
//            String description,
//            String thumbnailUrl,
//            ProductStatus status,
//            Category category
//    ) {
//        this.productName = productName;
//        this.productCode = productCode;
//        this.price = price;
//        this.stockQuantity = stockQuantity;
//        this.description = description;
//        this.thumbnailUrl = thumbnailUrl;
//        this.status = ProductStatus.ACTIVE;
//        this.category = category;
//    }

    // 상품 수정
    public void update(ProductRequest.UpdateDTO dto, Category category) {
        dto.validate();

        this.productName = dto.getProductName();
        this.productCode = dto.getProductCode();
        this.price = dto.getPrice();
        this.stockQuantity = dto.getStockQuantity();
        this.description = dto.getDescription();
        this.thumbnailUrl = dto.getThumbnailUrl();
        this.category = category;
        this.status = dto.getStatus();


        this.category = category;
        this.status = dto.getStatus();

        if (this.stockQuantity <= 0) {
            this.status = ProductStatus.SOLD_OUT;
        }
    }

    // 결제 후 수량 감소
    public void decreaseQuantity(int quantity) {
        this.stockQuantity -= quantity;
    }
    // 환불 후 수량 증가
    public void increaseQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    // 상태 변경
    public void updateStatus(ProductStatus status) {
        this.status = status;
    }


    public boolean isOwner(Long sessionUserId) {
        return this.owner.getUser().getId().equals(sessionUserId);
    }
}