package org.example.shopping.payment.dto;

import lombok.Data;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.paymentEnum.PaymentMethod;

public class PaymentRequest {

    @Data
    public static class CreateDTO {
        private PaymentMethod method;
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
