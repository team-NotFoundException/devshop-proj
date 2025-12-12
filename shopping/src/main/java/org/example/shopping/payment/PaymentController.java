package org.example.shopping.payment;

import lombok.RequiredArgsConstructor;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentPersistRepository repository;

    // 결제 생성
    // http://localhost:8080/payment/payment
    @GetMapping("/payment/payment")
    public String createPaymentForm(){
        return "payment/payment-form";
    }

    @PostMapping("/payment/payment")
    public String createPaymentProc(PaymentRequest.CreateDTO createDTO){

        Payment payment = Payment.builder()
//                .username(username)
                .orderId("ORD-"+ UUID.randomUUID())
                .paymentKey("MOCK-"+ UUID.randomUUID())
                .amount(createDTO.toEntity().getAmount())
                .method(PaymentMethod.MOCK)
                .status(PaymentStatus.SUCCESS)
                .productCode(createDTO.toEntity().getProductCode())
                .productName(createDTO.toEntity().getProductName())
                .build();
        payment.paySuccess();
        repository.save(payment);
        return "redirect:/";
    }

    // 결제 적용
    @GetMapping("/payment/{id}/approve")
    public String approvePaymentForm(@PathVariable Long id){
        return "payment/approve-form";
    }

    @PostMapping("/payment/{id}/approve")
    public String approvePaymentProc(@PathVariable Long id, PaymentRequest.ApproveDTO approveDTO){
        try{
//            repository.approveById(id, approveDTO);

        } catch (Exception e) {
            throw new RuntimeException("결제 승인 실패");
        }

        return "redirect:/";
    }

}
