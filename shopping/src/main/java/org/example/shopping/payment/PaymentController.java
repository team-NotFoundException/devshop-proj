package org.example.shopping.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentPersistRepository repository;

    // 결제 생성
    @GetMapping("/payment/payment")
    public String createPayment(){
        return "payment/payment-form";
    }

    @PostMapping("/payment/payment")
    public String createProc(PaymentRequest.CreateDTO createDTO){
        Payment payment = createDTO.toEntity();
        repository.save(payment);
        return "redirect:/";
    }

    // 결제 승인
}
