package org.example.shopping.category;

import lombok.Data;

public class CategoryRequest {

    @Data
    public static class CreateDTO {
    private String categoryName;
    private Long patentId;
    private int depth;
    private int displayOrder;

    public Category toEntity(Category parent) {
        return Category.builder()
                .categoryName(this.categoryName)
                .parent(parent)
                .depth(this.depth)
                .displayOrder(this.displayOrder)
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
}
