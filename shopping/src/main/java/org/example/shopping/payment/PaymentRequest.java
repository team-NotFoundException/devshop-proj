package org.example.shopping.payment;

import lombok.Data;
import org.example.shopping.payment.paymentEnum.PaymentMethod;

import java.time.LocalDateTime;

public class PaymentRequest {

    @Data
    public static class CreateDTO {
        private String productCode;
        private String productName;
        private Long amount;
        private PaymentMethod method;

        public Payment toEntity() {
           return Payment.builder()
                   .productCode(this.productCode)
                   .productName(this.productName)
                   .amount(this.amount)
                   .method(this.method)
                   .build();
        }
    }

    @Data
    public static class ApproveDTO{
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
        private Long amount;
        private String reason;

    }
}
