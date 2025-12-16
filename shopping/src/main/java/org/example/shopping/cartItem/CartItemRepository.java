package org.example.shopping.cartItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
   Optional<CartItem> findByCart_IdAndId(Long cartId, Long cartItemId);

   List<CartItem> findByCartId(Long cartId);

   @Modifying
   @Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId AND c.isChecked = true")
   void deleteByCartIdAndIsChecked(@Param("cartId") Long cartId);

}
