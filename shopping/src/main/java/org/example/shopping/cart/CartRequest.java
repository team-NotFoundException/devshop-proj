package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;

public class CartRequest {

    @Data
    public static class AddDTO {
        private String productId;
        private Integer quantity;

        public CartItem toEntity() {
            return new CartItem(productId, quantity);
        }
    }

    @Data
    public static class UpdateOptionDTO {
//        private String size;
        private Integer quantity;
    }
}
