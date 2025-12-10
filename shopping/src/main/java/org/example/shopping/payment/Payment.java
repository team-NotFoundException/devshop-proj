package org.example.shopping.payment;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.utils.BaseTimeEntity;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments_tb")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String orderId;

    private String paymentKey;

    private Long amount;

    private PaymentMethod method;

    private PaymentStatus status;

    private String productCode;

    private String productName;

    private String failureCode;

    private String failureMessage;

    @CreationTimestamp
    private LocalDateTime requestAt;

    private LocalDateTime approvedAt;

    private LocalDateTime cancelledAt;

    public Payment(String productCode, String productName, Long amount, PaymentMethod method){
        this.productCode = productCode;
        this.productName = productName;
        this.amount = amount;
        this.method = method;
    }

    // 결제 성공
    public void paySuccess() {
        this.status = PaymentStatus.SUCCESS;
        this.approvedAt = LocalDateTime.now();
        this.failureCode = null;
        this.failureMessage = null;
    }

    // 결제 실패 처리
    public void payFailed(String code, String message) {
        this.status = PaymentStatus.FAILED;
        this.failureCode = code;
        this.failureMessage = message;
    }

    // 환불 처리
    public void payRefund() {
        this.status = PaymentStatus.REFUNDED;
        this.cancelledAt = LocalDateTime.now();
    }
}
