package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;

public class CartResponse {

    @Data
    public static class CartItemListDTO {
        private String product;
        private Integer quantity;
        private boolean isChecked;

        public CartItemListDTO(CartItem cartItem) {
            this.product = cartItem.getProduct();
            this.quantity = cartItem.getQuantity();
            this.isChecked = cartItem.getIsChecked();
        }
    }
}
