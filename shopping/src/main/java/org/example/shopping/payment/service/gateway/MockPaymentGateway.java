package org.example.shopping.payment.service.gateway;

import org.example.shopping.payment.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class MockPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult approve(PaymentRequest.ApproveDTO approveDTO) {
        String paymentKey = "MOCK-" + UUID.randomUUID();

        return PaymentResult.ok(paymentKey);
    }
}
