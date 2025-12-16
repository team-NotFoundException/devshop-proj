package org.example.shopping.payment;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.service.PaymentService;
import org.example.shopping.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class PaymentController {


    private final PaymentService paymentService;

    // 결제 생성
    // http://localhost:8080/payment/payment
    @GetMapping("/payment/payment")
    public String createPaymentForm(
//            HttpSession session
    ) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
        return "payment/payment-form";
    }

    @PostMapping("/payment/payment")
    public String createPaymentProc(
//            HttpSession session,
            PaymentRequest.CreateDTO createDTO) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
        paymentService.createPayment(
//                sessionUser,
                createDTO);
        return "redirect:/";
    }


    // 결제 적용
    @GetMapping("/payment/{paymentId}/approve")
    public String approvePaymentForm(
//            HttpSession session,
            @PathVariable Long paymentId) {
//        User sessionUser = (User) session.getAttribute("sessionUser");

        return "payment/approve-form";
    }

    @PostMapping("/payment/{paymentId}/approve")
    public String approvePaymentProc(@PathVariable Long paymentId, PaymentRequest.ApproveDTO approveDTO) {
        try {
//            repository.approveById(paymentId, approveDTO);

        } catch (Exception e) {
            throw new RuntimeException("결제 승인 실패");
        }

        return "redirect:/";
    }



    @GetMapping("/payment/{id}/refund")
    public String refundPaymentForm(@PathVariable Long id, Model model
//            ,HttpSession session
    ) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
        PaymentResponse response =
        paymentService.refundPaymentForm(id
//                , sessionUser.getId()
        );
        model.addAttribute("payment", response);
        return "payment/refund-form";
    }

    @PostMapping("/payment/{id}/refund")
    public String refundPaymentProc(@PathVariable Long id, PaymentRequest.RefundDTO refundDTO
//            , HttpSession session
    ) {
//        User sessionUser = (User) session.getAttribute("sessionUser");

        paymentService.refundPayment(id, refundDTO
//                , sessionUser.getId()
        );
//        session.setAttribute("sessionUser", response);
        return "redirect:/";
    }


}
