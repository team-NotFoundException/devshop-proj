package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping._core.errors.exception.Exception400;

public class CartRequest {

    @Data
    public static class AddDTO {
        private Long productId;
        private Integer quantity;

        public void validate() {
            if (quantity <= 0)
                throw new Exception400("상품은 1개 이상이어야 합니다.");
        }
    }

    @Data
    public static class UpdateOptionDTO {
        private Integer quantity;
    }

    @Data
    public static class ToggleAllChecksDTO {
        private Boolean isChecked;
    }
}
