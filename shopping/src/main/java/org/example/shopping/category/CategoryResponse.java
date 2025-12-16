package org.example.shopping.category;

import lombok.Data;
import org.example.shopping._core.utils.MyDateUtil;

public class CategoryResponse {
    @Data
    public static class ListDTO {
        private Long id;
        private String categoryName;
        private int depth;
        private int displayOrder;
        private String parentName;
        private String createdAt;

        public ListDTO(Category category) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
            this.depth = category.getDepth();
            this.displayOrder = category.getDisplayOrder();

            if (category.getParent() != null) {
                this.parentName = category.getParent().getCategoryName();
            }

            if (category.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(category.getCreatedAt());
            }
        }
    }

    /**
     * 카테고리 상세 DTO
     */
    @Data
    public static class DetailDTO {
        private Long id;
        private String categoryName;
        private int depth;
        private int displayOrder;
        private Long parentId;
        private String parentName;
        private String createdAt;

        public DetailDTO(Category category) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
            this.depth = category.getDepth();
            this.displayOrder = category.getDisplayOrder();

            if (category.getParent() != null) {
                this.parentId = category.getParent().getId();
                this.parentName = category.getParent().getCategoryName();
            }

            if (category.getCreatedAt() != null) {
                this.createdAt = MyDateUtil.timestampFormat(category.getCreatedAt());
            }
        }
    }

    /**
     * 카테고리 수정 폼 DTO
     */
    @Data
    public static class UpdateFormDTO {
        private Long id;
        private String categoryName;
        private int displayOrder;

        public UpdateFormDTO(Category category) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
            this.displayOrder = category.getDisplayOrder();
        }
    }

}