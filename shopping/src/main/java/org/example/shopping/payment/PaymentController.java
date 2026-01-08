package org.example.shopping.payment;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.shopping.payment.dto.PaymentRequest;
import org.example.shopping.payment.dto.PaymentResponse;
import org.example.shopping.payment.service.PaymentService;
import org.example.shopping.users.User;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class PaymentController {


    private final PaymentService paymentService;
    private final Environment env;

    // 결제 생성
    @GetMapping("/payment")
    public String createPaymentForm(
            HttpSession session,
            Long cartId,
            Model model
    ) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (cartId != null) {
            PaymentResponse.CartPaymentDTO cartPayment = paymentService.getCartInfo(cartId);
            model.addAttribute("cartPayment", cartPayment);
            model.addAttribute("cartId", cartId);
        }
        String clientKey = env.getProperty("payment.toss.client-key");
        String baseUrl = "http://localhost:8080";
        model.addAttribute("tossClientKey", clientKey);
        model.addAttribute("baseUrl", baseUrl);
        return "payment/payment-form";
    }
    // ============================================================================================================

    // MOCK 결제
    @PostMapping("/payment/cart/{cartId}")
    public String createPayment(PaymentRequest.CreateDTO createDTO, HttpSession session, @PathVariable Long cartId, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        PaymentResponse response = paymentService.createPayment(createDTO, sessionUser, cartId);
        model.addAttribute("payment", response);
        return "redirect:/";

    }

    // ============================================================================================================

    // TOSS 결제
    @GetMapping("/payment/cart/{cartId}/approve")
    public String approvePaymentProc(HttpSession session, @PathVariable Long cartId, PaymentRequest.CreateDTO createDTO, Model model) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        try {
            PaymentResponse.PaymentResultDTO result = paymentService.approvePayment(sessionUser, cartId, createDTO);
            model.addAttribute("orderId", createDTO.getOrderId());
            model.addAttribute("amount", createDTO.getAmount());
            model.addAttribute("method", createDTO.getMethod());
            model.addAttribute("paymentKey", createDTO.getPaymentKey());
            model.addAttribute("items", result.getItems());
            return "payment/payment-success";

        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "payment/payment-fail";
        }
    }

    @GetMapping("/payment/cart/{cartId}/fail")
    public String failPaymentForm(@PathVariable Long cartId, String code, String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("code", code);
        model.addAttribute("cartId", cartId);
        return "payment/payment-fail";

    }

    //==============================================================================================================================

    //    @GetMapping("/payment/{id}/refund")
//    public String refundPaymentForm(@PathVariable Long id, Model model
//            ,HttpSession session
//    ) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//        PaymentResponse response =
//                paymentService.refundPaymentForm(id
//                        , sessionUser.getId()
//                );
//        model.addAttribute("payment", response);
//        return "payment/refund-form";
//    }
//
//    @PostMapping("/payment/{id}/refund")
//    public String refundPaymentProc(@PathVariable Long id, PaymentRequest.RefundDTO refundDTO
//            , HttpSession session
//    ) {
//        User sessionUser = (User) session.getAttribute("sessionUser");
//
//        PaymentRefund response = paymentService.refundPayment(id, refundDTO
//                , sessionUser.getId()
//        );
//        session.setAttribute("sessionUser", response);
//        return "redirect:/";
//    }


}
