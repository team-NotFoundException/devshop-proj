package org.example.shopping.payment.service;

import lombok.RequiredArgsConstructor;
import org.example.shopping.ResponseDto;
import org.example.shopping._core.errors.exception.Exception404;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.PaymentRepository;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.payment.paymentEnum.RefundStatus;
import org.example.shopping.payment.service.gateway.PaymentGateway;
import org.example.shopping.payment.service.gateway.PaymentGatewayResolver;
import org.example.shopping.payment.service.gateway.PaymentResult;
import org.example.shopping.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRefundRepository refundRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGatewayResolver gatewayResolver;

    @Transactional
    public ResponseDto<?> createPayment(
//            User sessionUser,
                                        PaymentRequest.CreateDTO createDTO) {
        return switch (createDTO.getMethod()){
            case MOCK -> ResponseDto.success(processMockPayment(
//                    sessionUser,
                    createDTO));
            case TOSS_PAY -> null;
        };
    }

    private PaymentResponse processMockPayment(
//            User sessionUser,
                                               PaymentRequest.CreateDTO createDTO) {
        Payment payment = Payment.builder()
//                .user(sessionUser)
                .orderId("ORD-"+ UUID.randomUUID())
                .paymentKey("MOCK-"+ UUID.randomUUID())
                .amount(createDTO.toEntity().getAmount())
                .method(PaymentMethod.MOCK)
                .status(PaymentStatus.SUCCESS)
                .productCode(createDTO.toEntity().getProductCode())
                .productName(createDTO.toEntity().getProductName())
                .build();
        payment.paySuccess();
        paymentRepository.save(payment);
        return toDto(payment);
    }

    @Transactional
    public PaymentResponse approvePayment(PaymentRequest.ApproveDTO approveDTO) {
        PaymentGateway gateway = gatewayResolver.resolve(approveDTO.getMethod());
        PaymentResult result = gateway.approve(approveDTO);

        Payment payment = Payment.builder()
//                .username(username)
                .orderId(approveDTO.getOrderId())
                .paymentKey(result.getPaymentKey())
                .amount(approveDTO.getAmount())
                .method(approveDTO.getMethod())
                .status(result.isSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                .productCode(approveDTO.getProductCode())
                .productName(approveDTO.getProductName())
                .failureCode(result.getFailureCode())
                .failureMessage(result.getFailureMessage())
                .build();
        if (result.isSuccess()) {
            payment.paySuccess();
        }
        paymentRepository.save(payment);
        return toDto(payment);
    }



    public PaymentResponse refundPaymentForm(Long id
//            , Long sessionUserId
    ) {
        Payment paymentEntity = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception404("찾을 수 없음"));

        return new PaymentResponse(paymentEntity);
    }

    @Transactional
    public void refundPayment(Long id, PaymentRequest.RefundDTO refundDTO
//            , Long sessionUserId
    ) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new Exception404("결제 내역 찾을 수 없음"));

        PaymentRefund refund = PaymentRefund.builder()
                .payment(payment)
                .amount(refundDTO.getAmount())
                .reason(refundDTO.getReason())
                .status(RefundStatus.REQUESTED)
                .requestedAt(LocalDateTime.now())
                .build();

        refund.refundCompleted();
        payment.payRefund();

        refundRepository.save(refund);
        paymentRepository.save(payment);

    }


    private PaymentResponse toDto(Payment p) {
        return new PaymentResponse(
                p.getId(),
                p.getOrderId(),
                p.getPaymentKey(),
                p.getAmount(),
                p.getMethod(),
                p.getStatus(),
                p.getProductCode(),
                p.getProductName(),
                p.getRequestedAt(),
                p.getApprovedAt()
        );
    }


}
