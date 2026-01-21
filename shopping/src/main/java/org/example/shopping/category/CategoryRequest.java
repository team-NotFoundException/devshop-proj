package org.example.shopping.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CategoryRequest {


    // 카테고리 등록 DTO

    @Data
    public static class SaveChildDTO {
        @NotBlank(message = "카테고리명은 필수입니다.")
        private String categoryName;

        @Min(value = 0, message = "0보다 커야합니다.")
        private Long displayOrder;

        private Long parentId;

        public Category toEntity(Category parentEntity,String savedFileName) {
            return Category.builder()
                    .categoryName(this.categoryName)
                    .depth(parentEntity.getDepth() + 1)
                    .displayOrder(this.displayOrder)
                    .imageUrl(savedFileName)
                    .parent(parentEntity)
                    .build();
        }

    }

    @Data
    public static class SaveParentDTO{
        @NotBlank(message = "카테고리명은 필수입니다.")
        private String categoryName;

        private int depth;

        @Min(value = 0, message = "0보다 커야합니다.")
        private Long displayOrder;

        public Category toEntity(String savedFileName) {
            return Category.builder()
                    .categoryName(this.categoryName)
                    .imageUrl(savedFileName)
                    .depth(this.depth = 1)
                    .displayOrder(this.displayOrder)
                    .build();
        }
    }



    // 카테고리 수정 DTO
    @Data
    public static class UpdateDTO {
        @NotBlank(message = "카테고리명은 필수입니다.")
        private String categoryName;   // 필수

        @Min(value = 0, message = "0보다 커야합니다.")
        private Long displayOrder;  // 필수
    }
}