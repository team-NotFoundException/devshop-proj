package org.example.shopping.cart;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.cartItem.CartItem;

import java.util.List;


@Data
@NoArgsConstructor
@Table(name = "cart_tb")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String user;

    Long totalPrice;

    public Cart(Long totalPrice, String user) {
        this.totalPrice = totalPrice;
        this.user = user;
    }
}
