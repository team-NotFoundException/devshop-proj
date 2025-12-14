package org.example.shopping.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemPersistRepository extends JpaRepository<CartItem, Long> {
}
