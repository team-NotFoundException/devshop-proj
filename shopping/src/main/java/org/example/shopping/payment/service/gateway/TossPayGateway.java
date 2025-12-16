package org.example.shopping.payment.service.gateway;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shopping.payment.dto.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TossPayGateway implements PaymentGateway {
    @Override
    public PaymentResult approve(PaymentRequest.ApproveDTO approveDTO) {
        return null;
    }
}
