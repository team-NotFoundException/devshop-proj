package org.example.shopping.payment.dto;

import lombok.Data;
import org.example.shopping.payment.paymentEnum.PaymentMethod;

public class PaymentRequest {



    @Data
    public static class CreateDTO {
        private String paymentKey;
        private String orderId;
        private Long amount;

        private PaymentMethod method;

        private String productCode;
        private String productName;

        private String failureCode;
        private String failureMessage;

    }

    @Data
    public static class RefundDTO {
        private String paymentKey;
        private Long amount;
        private String reason;
    }
}
