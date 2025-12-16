package org.example.shopping.product;

import lombok.Data;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.category.Category;
import org.example.shopping.product.productEnum.ProductStatus;

import java.math.BigDecimal;

public class ProductRequest {


    // 상품 등록
    @Data
    public static class SaveDTO {
        private Long categoryId;
        private String productName;
        private String productCode;
        private BigDecimal price;
        private int stockQuantity;
        private String description;
        private String thumbnailUrl;

        public void validate() {
            if (productName == null || productName.isBlank()) {
                throw new Exception400("상품명은 필수입니다.");
            }
            if (productCode == null || productCode.isBlank()) {
                throw new Exception400("상품 코드는 필수입니다.");
            }
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception400("가격은 0보다 커야 합니다.");
            }
        }

        public Product toEntity(Category category) {
            return Product.builder()
                    .category(category)
                    .productName(productName)
                    .productCode(productCode)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .description(description)
                    .thumbnailUrl(thumbnailUrl)
                    .status(ProductStatus.active)
                    .build();
        }
    }

    // 상품 수정
    @Data
    public static class UpdateDTO {
        private Long categoryId;     // 수정 시 카테고리도 선택 가능
        private String productName;
        private BigDecimal price;
        private int stockQuantity;
        private String description;
        private String thumbnailUrl;
        private ProductStatus status;

        public void validate() {
            if (productName == null || productName.isBlank()) {
                throw new Exception400("상품명은 필수입니다.");
            }
            if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception400("가격은 0보다 커야 합니다.");
            }
        }
    }


    // 재고 수정
    @Data
    public static class UpdateStockDTO {
        private int stockQuantity;

        public void validate() {
            if (stockQuantity < 0) {
                throw new Exception400("재고는 0보다 작을 수 없습니다.");
            }
        }
    }


    // 상태 변경
    @Data
    public static class UpdateStatusDTO {
        private ProductStatus status;

        public void validate() {
            if (status == null) {
                throw new Exception400("상품 상태는 필수입니다.");
            }
        }
    }
}