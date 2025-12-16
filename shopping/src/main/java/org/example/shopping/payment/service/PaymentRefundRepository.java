package org.example.shopping.payment.service;

import org.example.shopping.payment.PaymentRefund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRefundRepository extends JpaRepository<PaymentRefund, Long> {
}
