package org.example.shopping.payment;

import org.example.shopping.cartItem.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
