package org.example.shopping.category;

import lombok.Data;

public class CategoryRequest {

    /**
     * 카테고리 등록 DTO
     */
    @Data
    public static class SaveDTO {
        private String categoryName;   // 필수
        private Integer depth;          // 필수
        private Integer displayOrder;   // 필수
        private Long parentId;          // 선택 (최상위면 null)

        public void validate() {
            if (categoryName == null || categoryName.trim().isEmpty()) {
                throw new IllegalArgumentException("카테고리명은 필수입니다");
            }
            if (depth == null || depth < 0) {
                throw new IllegalArgumentException("depth 값이 올바르지 않습니다");
            }
            if (displayOrder == null || displayOrder < 0) {
                throw new IllegalArgumentException("displayOrder 값이 올바르지 않습니다");
            }
        }
    }

    /**
     * 카테고리 수정 DTO
     */
    @Data
    public static class UpdateDTO {
        private String categoryName;   // 필수
        private Integer displayOrder;  // 필수

        public void validate() {
            if (categoryName == null || categoryName.trim().isEmpty()) {
                throw new IllegalArgumentException("카테고리명은 필수입니다");
            }
            if (displayOrder == null || displayOrder < 0) {
                throw new IllegalArgumentException("displayOrder 값이 올바르지 않습니다");
            }
        }
    }
}