package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;

import java.util.List;

public class CartResponse {

    @Data
    public static class CartDTO {
        private Long id;
        private Long amount;
        private List<CartItem> cartItems;

        public CartDTO(Cart cart) {
            this.id = cart.getId();
            this.amount = cart.getAmount() == null ? 0 : cart.getAmount();
            this.cartItems = cart.getCartItems();
        }
    }

    @Data
    public static class AmountDTO {
        private Long amount;

        public AmountDTO(Long amount) {
            this.amount = amount;
        }
    }
}
