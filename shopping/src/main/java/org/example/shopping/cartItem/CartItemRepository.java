package org.example.shopping.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
   Optional<CartItem> findByCart_IdAndId(Long cartId, Long cartItemId);
}
