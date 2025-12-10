package org.example.shopping.payment;

import lombok.Data;
import org.example.shopping.payment.paymentEnum.PaymentMethod;

public class PaymentRequest {

    @Data
    public static class CreateDTO{
        private String productCode;
        private String productName;
        private Long amount;
        private PaymentMethod method;

        public Payment toEntity(){
            return new Payment(productCode, productName, amount, method);
        }
    }
}
