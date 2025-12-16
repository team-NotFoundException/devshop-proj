package org.example.shopping.payment.service.gateway;

import lombok.RequiredArgsConstructor;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentGatewayResolver {
    private final MockPaymentGateway mockPaymentGateway;
    private final TossPayGateway tossPayGateway;

    public PaymentGateway resolve(PaymentMethod method){
        return switch (method){
            case MOCK -> mockPaymentGateway;
            case TOSS_PAY ->  tossPayGateway;
        };
    }
}
