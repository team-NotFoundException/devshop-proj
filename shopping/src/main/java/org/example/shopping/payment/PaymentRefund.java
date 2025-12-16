package org.example.shopping.payment;


import jakarta.persistence.*;
import lombok.*;
import org.example.shopping._core.utils.BaseTimeEntity;
import org.example.shopping.payment.paymentEnum.RefundStatus;


import java.time.LocalDateTime;

@Entity
@Table(name = "payment_refund")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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


    public void refundCompleted(){
        this.status = RefundStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
        this.failureCode = null;
        this.failureMessage = null;
    }
    public void refundFailed(String code, String message){
        this.status = RefundStatus.FAILED;
        this.failureCode = code;
        this.failureMessage = message;
    }
}
