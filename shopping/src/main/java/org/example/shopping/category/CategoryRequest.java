package org.example.shopping.category;

import lombok.Data;

public class CategoryRequest {

    @Data
    public static class CreateDTO {
        private String categoryName;
        private Long parentId;

        public Category toEntity(Category parent) {
            return Category.builder()
                    .categoryName(this.categoryName)
                    .parent(parent)
                    .depth(parent == null ? 0 : parent.getDepth() + 1)
                    .displayOrder(0)
                    .build();
        }
    }
    @Data
    public static class UpdateDTO {
        private String categoryName;
        private Long patentId;
        private int depth;
        private int displayOrder;
    }

    @Data
    public class SaveDTO {

    }


}
