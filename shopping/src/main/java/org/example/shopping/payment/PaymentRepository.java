package org.example.shopping.payment;

import org.example.shopping.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByUserId(Long userId);

    @Query("SELECT p FROM Payment p where p.user.id = :id")
    List<Payment> findInfoUserId(@Param("id") Long userId);


}
