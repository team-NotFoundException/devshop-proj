package org.example.shopping.category;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping._core.utils.MyDateUtil;

import java.util.List;

public class CategoryResponse {

    @Data
    @NoArgsConstructor
    public static class ListDTO {
        private Long id;
        private String categoryName;
        private int depth;
        private Integer displayOrder;
        private String parentName;
        private String createdAt;

        public ListDTO(Category category) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
            this.depth = category.getDepth();
            this.displayOrder = (category.getDisplayOrder() != null) ? category.getDisplayOrder() : 0;

            if (category.getParent() != null) {
                this.parentName = category.getParent().getCategoryName();
            }
        }

        public boolean isRoot() {
            return this.depth == 1;
        }
    }


    // 카테고리 상세 DTO
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

        }
    }

    // 카테고리 수정 폼 DTO
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


    @Data
    public static class NavbarDTO {
        private Long id;
        private String categoryName;
        private List<NavbarDTO> children;

        public NavbarDTO(Category category, List<NavbarDTO> children) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
            this.children = children;
        }

        public NavbarDTO(Category category) {
            this.id = category.getId();
            this.categoryName = category.getCategoryName();
        }
    }

}