package org.example.shopping.cart;

import lombok.Data;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.product.Product;

public class CartRequest {

    @Data
    public static class AddDTO {
        private Cart cart;
        private Product product;
        private Integer quantity;

        public CartItem toEntity(Cart cart, Product product) {
            return new CartItem(cart, product , quantity);
        }
    }

    @Data
    public static class UpdateOptionDTO {
        private Integer quantity;
    }
}
