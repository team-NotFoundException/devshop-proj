package org.example.shopping.payment.service.gateway;

import org.example.shopping.payment.dto.PaymentRequest;

public interface PaymentGateway {

    PaymentResult approve(PaymentRequest.CreateDTO createDTO);
    PaymentResult refund(PaymentRequest.RefundDTO refundDTO);
}
