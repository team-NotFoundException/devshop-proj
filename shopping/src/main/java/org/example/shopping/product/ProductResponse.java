package org.example.shopping.product;

import lombok.Data;
import org.apache.catalina.User;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.product.productEnum.ProductStatus;


public class ProductResponse {

    @Data
    public static class MainCardDTO {
        private Long id;
        private String name;
        private String thumbnailUrl;
        private long price;
        private String ownerName;

        public MainCardDTO(Product product) {
            this.id = product.getId();
            this.name = product.getProductName();
            this.thumbnailUrl = product.getThumbnailUrl();
            this.price = product.getPrice();
            this.ownerName = product.getOwner().getName();
        }

    }
    @Data
    public static class UserDetailDTO {
        private Long id;
        private String name;
        private String thumbnailUrl;
        private long price;
        private String description;

        private Long categoryId;
        private String categoryName;

        public UserDetailDTO(Product product) {
            this.id = product.getId();
            this.name = product.getProductName();
            this.thumbnailUrl = product.getThumbnailUrl();
            this.price = product.getPrice().intValue();
            this.description = product.getDescription();

            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getCategoryName();
        }
    }


     // 상품 목록 응답 DTO
    @Data
    public static class ListDTO {
        private Long id;
        private String productName;
        private String categoryName;
        private int price;
        private int stockQuantity;
        private String status;
        private String createdAt;
        private String thumbnailUrl;

        public ListDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.price = product.getPrice().intValue();
            this.status = product.getStatus().name();
            this.thumbnailUrl = product.getThumbnailUrl();
            this.stockQuantity = product.getStockQuantity();

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }

            if (product.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(product.getCreatedAt());
            }

        }

    }


    // 상품 상세 응답 DTO
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


    // 상품 수정 화면 DTO
    @Data
    public static class UpdateFormDTO {
        private Long id;
        private String productName;
        private String productCode;
        private int price;
        private int stockQuantity;
        private String description;
        private String thumbnailUrl;
        private Long categoryId;
        private String status;

        private boolean active;
        private boolean soldOut;

        public UpdateFormDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.productCode = product.getProductCode();
            this.price = product.getPrice().intValue();
            this.stockQuantity = product.getStockQuantity();
            this.description = product.getDescription();
            this.thumbnailUrl = product.getThumbnailUrl();
            this.status = product.getStatus().name();

            if (product.getStatus() == ProductStatus.ACTIVE) {
                this.active = true;
            }
            if (product.getStatus() == ProductStatus.SOLD_OUT) {
                this.soldOut = true;
            }

            if (product.getCategory() != null) {
                this.categoryId = product.getCategory().getId();
            }
        }
    }
}