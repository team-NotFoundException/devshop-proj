package org.example.shopping.cart;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.cartItem.CartItem;

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

    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    private Long totalPrice;

    public Cart(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addItem(CartItem item) {
        item.setCart(this);
        this.cartItems.add(item);
    }

    public void removeItem(CartItem item) {
        this.cartItems.remove(item);
        item.setCart(null);
    }


}
