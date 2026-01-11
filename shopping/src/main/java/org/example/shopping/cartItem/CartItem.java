package org.example.shopping.cartItem;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping._core.errors.exception.Exception400;
import org.example.shopping.cart.Cart;
import org.example.shopping.product.Product;

@Data
@NoArgsConstructor
@Table(name = "cart_item_tb")
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    private Product product;

    private Boolean isChecked;

    private Integer quantity;

    private Long totalPrice;

    @Builder
    public CartItem(Cart cart ,Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
        this.isChecked = true;
    }

    public boolean isItemChecked() {
        return this.isChecked;
    }

    public void toggleItem() {
        this.isChecked = !this.isChecked;
    }

    public void updateQuantity(Integer quantity) {
        this.quantity = quantity;

        if (this.quantity + quantity <= 0) {
            throw new Exception400("상품은 0개 이하일 수 없습니다.");
        }
    }

    public void updateTotalPrice() {
        this.totalPrice = this.quantity * this.product.getPrice();
    }
}
