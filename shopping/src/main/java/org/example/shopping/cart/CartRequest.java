package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.product.Product;

import java.math.BigDecimal;

public class CartRequest {

    @Data
    public static class AddDTO {
        private Integer quantity;
        private Long totalPrice;

        public CartItem toEntity(Product product) {
            return CartItem.builder()
                    .quantity(this.quantity)
                    .totalPrice(this.totalPrice)
                    .build();
        }
    }

    @Data
    public static class UpdateOptionDTO {
        private Integer quantity;
    }


}
