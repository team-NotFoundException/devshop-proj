package org.example.shopping.payment.service.gateway;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentResult {
    private boolean success;
    private String paymentKey;
    private String failureCode;
    private String failureMessage;

    public static PaymentResult ok(String paymentKey){
        return PaymentResult.builder()
                .success(true)
                .paymentKey(paymentKey)
                .build();
    }

    public static PaymentResult fail(String failureCode, String failureMessage) {
        return PaymentResult.builder()
                .success(false)
                .failureCode(failureCode)
                .failureMessage(failureMessage)
                .build();
    }

}
