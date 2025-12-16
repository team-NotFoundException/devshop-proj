package org.example.shopping.cart;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.user.User;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@Table(name = "cart_tb")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne
//    private User user;
    
    private Long totalPrice;

    public Cart(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public CartItem addItem(String productId, Integer quantity) {
        CartItem cartItem = new CartItem(this, productId, quantity);
        return cartItem;
    }

}
