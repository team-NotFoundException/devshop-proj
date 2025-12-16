package org.example.shopping.payment.service.gateway;

import org.example.shopping.payment.dto.PaymentRequest;

public interface PaymentGateway {

    PaymentResult approve(PaymentRequest.ApproveDTO approveDTO);

}
