package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;

public class CartResponse {

    @Data
    public static class CartItemListDTO {
        private Long id;
        private Long cartId;
        private String product;
        private Integer quantity;
        private boolean isChecked;

        public CartItemListDTO(CartItem cartItem) {
            this.id = cartItem.getId();
            this.cartId = cartItem.getCart().getId();
            this.product = cartItem.getProduct();
            this.quantity = cartItem.getQuantity();
            this.isChecked = cartItem.getIsChecked();
        }
    }
}
