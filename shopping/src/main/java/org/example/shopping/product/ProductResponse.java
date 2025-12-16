package org.example.shopping.product;

import lombok.Data;
import org.example.shopping._core.utils.MyDateUtil;


public class ProductResponse {

    /**
     * 상품 목록 응답 DTO
     */
    @Data
    public static class ListDTO {
        private Long id;
        private String productName;
        private String categoryName;
        private int price;
        private String status;
        private String createdAt;

        public ListDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.price = product.getPrice().intValue();
            this.status = product.getStatus().name();

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }

            if (product.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(product.getCreatedAt());
            }

        }
    }

    /**
     * 상품 상세 응답 DTO
     */
    @Data
    public static class DetailDTO {
        private Long id;
        private String productName;
        private String productCode;
        private int price;
        private int stockQuantity;
        private String description;
        private String thumbnailUrl;
        private String categoryName;
        private String status;
        private String createdAt;

        public DetailDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.productCode = product.getProductCode();
            this.price = product.getPrice().intValue();
            this.stockQuantity = product.getStockQuantity();
            this.description = product.getDescription();
            this.thumbnailUrl = product.getThumbnailUrl();
            this.status = product.getStatus().name();

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }

            if (product.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(product.getCreatedAt());
            }

        }
    }

    /**
     * 상품 수정 화면 DTO
     */
    @Data
    public static class UpdateFormDTO {
        private Long id;
        private String productName;
        private int price;
        private int stockQuantity;
        private String description;
        private String thumbnailUrl;
        private Long categoryId;
        private String status;

        public UpdateFormDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.price = product.getPrice().intValue();
            this.stockQuantity = product.getStockQuantity();
            this.description = product.getDescription();
            this.thumbnailUrl = product.getThumbnailUrl();
            this.status = product.getStatus().name();

            if (product.getCategory() != null) {
                this.categoryId = product.getCategory().getId();
            }
        }
    }
}