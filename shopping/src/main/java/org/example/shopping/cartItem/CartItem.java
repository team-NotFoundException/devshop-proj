package org.example.shopping.cartItem;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.cart.Cart;

@Data
@NoArgsConstructor
@Table(name = "cart_item_tb")
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    Cart cart;

    String product;

    Integer quantity;

    Long orderPrice;

    Long totalPrice;

    public CartItem(Cart cart, Long orderPrice, String product, Integer quantity, Long totalPrice) {
        this.cart = cart;
        this.orderPrice = orderPrice;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
