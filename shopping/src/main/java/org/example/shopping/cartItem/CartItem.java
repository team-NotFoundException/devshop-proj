package org.example.shopping.cartItem;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @OneToOne
    private Product product;

    private Boolean isChecked;

    private Integer quantity;

    @Builder
    public CartItem(Cart cart, Product product, Integer quantity) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.isChecked = true;
    }

    public boolean isItemChecked(boolean isChecked) {
        return isChecked;
    }

    public void updateItemOption(Integer quantity) {
        this.quantity = quantity;
    }

    public void updateCheckItem() {
        this.isChecked = !this.isChecked;
    }
}
