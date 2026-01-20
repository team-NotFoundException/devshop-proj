package org.example.shopping.product;

import lombok.Data;
import org.example.shopping._core.utils.MoneyUtils;
import org.example.shopping._core.utils.MyDateUtil;
import org.example.shopping.product.productEnum.ProductStatus;

public class ProductResponse {

    // 이미지 경로 처리 공통 메서드
    private static String getImagePath(String thumbnailUrl) {
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            return "/img/no-image.png";
        }
        // http:// 또는 https://로 시작하면 외부 URL이므로 그대로 반환
        if (thumbnailUrl.startsWith("http://") || thumbnailUrl.startsWith("https://")) {
            return thumbnailUrl;
        }
        // 로컬 파일이면 /images/ 경로 추가
        return "/images/" + thumbnailUrl;
    }

    @Data
    public static class MainCardDTO {
        private Long id;
        private String name;
        private String thumbnailUrl;
        private String price;
        private String ownerName;
        private Long categoryId;
        private String categoryName;

        public MainCardDTO(Product product) {
            this.id = product.getId();
            this.name = product.getProductName();
            this.thumbnailUrl = getImagePath(product.getThumbnailUrl());
            this.price = MoneyUtils.format(product.getPrice());
            this.ownerName = product.getOwner().getName();


            if (product.getCategory() != null) {
                this.categoryId = product.getCategory().getId();
                this.categoryName = product.getCategory().getCategoryName();
            }
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
            this.thumbnailUrl = getImagePath(product.getThumbnailUrl());
            this.price = product.getPrice().intValue();
            this.description = product.getDescription();
            this.categoryId = product.getCategory().getId();
            this.categoryName = product.getCategory().getCategoryName();
        }
    }

    @Data
    public static class ListDTO {
        private Long id;
        private String productName;
        private String categoryName;
        private int price;
        private int stockQuantity;
        private int minusQuantity;
        private String status;
        private String createdAt;
        private String thumbnailUrl;

        public ListDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.price = product.getPrice().intValue();
            this.status = product.getStatus().name();
            this.thumbnailUrl = getImagePath(product.getThumbnailUrl());
            this.stockQuantity = product.getStockQuantity();
            this.minusQuantity = product.getMinusQuantity();

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }

            if (product.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(product.getCreatedAt());
            }
        }
    }

    @Data
    public static class DetailDTO {
        private Long id;
        private String productName;
        private String productCode;
        private int price;
        private int stockQuantity;
        private int minusQuantity;
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
            this.minusQuantity = product.getMinusQuantity();
            this.description = product.getDescription();
            this.thumbnailUrl = getImagePath(product.getThumbnailUrl());
            this.status = product.getStatus().name();

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }

            if (product.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(product.getCreatedAt());
            }
        }
    }

    @Data
    public static class UpdateFormDTO {
        private Long id;
        private String productName;
        private String productCode;
        private int price;
        private int stockQuantity;
        private String description;
        private String thumbnailUrl;  // 수정 폼에서는 원본 경로 필요
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
            // 수정 폼에서는 원본 thumbnailUrl 필요 (DB 저장용)
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

        // 수정 폼의 이미지 미리보기를 위한 메서드
        public String getThumbnailDisplayUrl() {
            return getImagePath(this.thumbnailUrl);
        }
    }

    // ProductResponse.java 파일 끝에 다음 DTO들을 추가하세요

    @Data
    public static class AdminListDTO {
        private Long id;
        private String productName;
        private String ownerName;
        private String categoryName;
        private int price;
        private int stockQuantity;
        private String status;

        public AdminListDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.price = product.getPrice().intValue();
            this.status = product.getStatus().name();
            this.stockQuantity = product.getStockQuantity();

            if (product.getOwner() != null) {
                this.ownerName = product.getOwner().getName();
            }

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }
        }
    }

    @Data
    public static class AdminDetailDTO {
        private Long id;
        private String productName;
        private String productCode;
        private int price;
        private int stockQuantity;
        private int minusQuantity;
        private String description;
        private String thumbnailUrl;
        private String ownerName;
        private String categoryName;
        private String status;
        private String createdAt;

        public AdminDetailDTO(Product product) {
            this.id = product.getId();
            this.productName = product.getProductName();
            this.productCode = product.getProductCode();
            this.price = product.getPrice().intValue();
            this.stockQuantity = product.getStockQuantity();
            this.minusQuantity = product.getMinusQuantity();
            this.description = product.getDescription();
            this.thumbnailUrl = getImagePath(product.getThumbnailUrl());
            this.status = product.getStatus().name();

            if (product.getOwner() != null) {
                this.ownerName = product.getOwner().getName();
            }

            if (product.getCategory() != null) {
                this.categoryName = product.getCategory().getCategoryName();
            }

            if (product.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(product.getCreatedAt());
            }
        }
    }
}
