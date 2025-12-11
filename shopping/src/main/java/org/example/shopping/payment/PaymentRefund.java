package org.example.shopping.payment;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shopping.payment.paymentEnum.RefundStatus;
import org.example.shopping.utils.BaseTimeEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_refund")
@Getter
@Setter
@NoArgsConstructor
public class PaymentRefund extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    private Long amount;

    private String reason;

    private RefundStatus status;

    private String failureCode;

    private String failureMessage;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

}
