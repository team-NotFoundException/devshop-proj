package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping._core.utils.MoneyUtils;
import org.example.shopping.cartItem.CartItem;

import java.util.List;

public class CartResponse {

    @Data
    public static class CartDTO {
        private Long id;
        private String amount;
        private List<CartItem> cartItems;

        public CartDTO(Cart cart) {
            this.id = cart.getId();
            Long amount = cart.getAmount() == null ? 0 : cart.getAmount();
            this.amount = MoneyUtils.format(amount);
            this.cartItems = cart.getCartItems();
        }
    }

    @Data
    public static class AmountDTO {
        private String amount;
        public AmountDTO(Long amount) {
            this.amount = MoneyUtils.format(amount);
        }
    }

    @Data
    public static class CartUpdateDTO {
        private Long id;
        private Integer quantity;
        private String totalPrice;
        private String amount;

        public CartUpdateDTO(Long id, Integer quantity, Long totalPrice, Long amount) {
            this.id = id;
            this.quantity = quantity;
            this.totalPrice = MoneyUtils.format(totalPrice);
            this.amount = MoneyUtils.format(amount);
        }
    }

    @Data
    public static class ToggleAllChecksDTO {
        private boolean allChecked;
        private String amount;

        public ToggleAllChecksDTO(boolean allChecked, Long amount) {
            this.allChecked = allChecked;
            this.amount = MoneyUtils.format(amount);
        }
    }
}
