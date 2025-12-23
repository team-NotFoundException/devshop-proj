package org.example.shopping.payment;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.service.PaymentService;
import org.example.shopping.users.User;
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
    @GetMapping("/payment")
    public String createPaymentForm(
            HttpSession session,
            Long cartId,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(cartId!=null){
            PaymentResponse.CartPaymentDTO cartPayment = paymentService.getCartInfo(cartId);
            model.addAttribute("cartPayment", cartPayment);
            model.addAttribute("cartId", cartId);
        }
        return "payment/payment-form";
    }

    @PostMapping("/payment/cart/{cartId}")
    public String createPaymentProc(
            HttpSession session,
            @PathVariable Long cartId,
            PaymentRequest.CreateDTO createDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        paymentService.createPayment(sessionUser, cartId, createDTO);
        return "redirect:/";
    }



    // 결제 적용
    @GetMapping("/payment/{paymentId}/approve")
    public String approvePaymentForm(
            HttpSession session,
            @PathVariable Long paymentId) {
        User sessionUser = (User) session.getAttribute("sessionUser");

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
            ,HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        PaymentResponse response =
        paymentService.refundPaymentForm(id
                , sessionUser.getId()
        );
        model.addAttribute("payment", response);
        return "payment/refund-form";
    }

    @PostMapping("/payment/{id}/refund")
    public String refundPaymentProc(@PathVariable Long id, PaymentRequest.RefundDTO refundDTO
            , HttpSession session
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        PaymentRefund response = paymentService.refundPayment(id, refundDTO
                , sessionUser.getId()
        );
        session.setAttribute("sessionUser", response);
        return "redirect:/";
    }


}
