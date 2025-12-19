package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.product.Product;

public class CartResponse {

    @Data
    public static class CartItemListDTO {
        private Long id;
        private Long cartId;
        private Product product;
        private Integer quantity;
        private Long totalPrice;
        private boolean isChecked;

        public CartItemListDTO(CartItem cartItem) {
            this.id = cartItem.getId();
            this.cartId = cartItem.getCart().getId();
            this.product = cartItem.getProduct();
            this.quantity = cartItem.getQuantity();
            this.totalPrice = cartItem.getTotalPrice();
            this.isChecked = cartItem.getIsChecked();
        }
    }
}
