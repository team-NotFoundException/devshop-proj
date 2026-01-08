package org.example.shopping.cart;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.cartItem.CartItem;
import org.example.shopping.users.User;

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

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<CartItem> cartItems = new ArrayList<>();

    private Long amount;

    public Cart(User user) {
        this.user = user;
    }

    public void addItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    public void removeCheckedItem() {
        this.cartItems.removeIf(CartItem::getIsChecked);
    }

    public void removeItem(Long cartItemId) {
        CartItem cartItem = this.cartItems.stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        cartItems.remove(cartItem);
    }

    public void clearItems() {
        this.cartItems.clear();
    }

    public void toggleItem(Long cartItemId) {
        CartItem cartItem = this.cartItems.stream()
                .filter(ci -> ci.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new Exception404("상품을 찾을 수 없습니다"));

        cartItem.toggleItem();
    }

    // 카트 총액 업데이트
    public void updateAmount() {

        this.amount = cartItems.stream()
                .filter(CartItem::isItemChecked)
                .mapToLong(CartItem::getTotalPrice)
                .sum();
    }
}