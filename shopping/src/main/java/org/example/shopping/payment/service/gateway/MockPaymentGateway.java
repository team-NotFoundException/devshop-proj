package org.example.shopping.payment.service.gateway;

import org.example.shopping.payment.dto.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class MockPaymentGateway implements PaymentGateway {
    @Override
    public PaymentResult approve(PaymentRequest.CreateDTO createDTO) {
        String paymentKey = "MOCK-" + UUID.randomUUID();

        return PaymentResult.ok(paymentKey);
    }

    @Override
    public PaymentResult refund(PaymentRequest.RefundDTO refundDTO) {
        return PaymentResult.ok(refundDTO.getPaymentKey());
    }

}
