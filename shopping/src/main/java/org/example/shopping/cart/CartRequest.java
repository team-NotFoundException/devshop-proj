package org.example.shopping.cart;

import lombok.Data;

public class CartRequest {

    @Data
    public static class AddDTO {
        private Integer quantity;
        private Long totalPrice;
    }

    @Data
    public static class UpdateOptionDTO {
        private Integer quantity;
    }
}
