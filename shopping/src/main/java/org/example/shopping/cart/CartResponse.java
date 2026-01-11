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

    @Data
    public static class CartUpdateDTO {
        private Long id;
        private Integer quantity;
        private Long totalPrice;
        private Long amount;

        public CartUpdateDTO(Long id, Integer quantity, Long totalPrice, Long amount) {
            this.id = id;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
            this.amount = amount;
        }
    }

    @Data
    public static class ToggleAllChecksDTO {
        private boolean allChecked;
        private Long amount;

        public ToggleAllChecksDTO(boolean allChecked, Long amount) {
            this.allChecked = allChecked;
            this.amount = amount;
        }
    }
}
