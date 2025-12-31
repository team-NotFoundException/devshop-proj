package org.example.shopping.payment;


import jakarta.persistence.*;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.example.shopping._core.utils.BaseTimeEntity;
import org.example.shopping.order.Order;
import org.example.shopping.orderItem.OrderItem;
import org.example.shopping.payment.paymentEnum.RefundStatus;
import org.example.shopping.users.User;


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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private Long amount;

    private String reason;

    private RefundStatus status;

    private String failureCode;

    private String failureMessage;

    private LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    @Builder
    public PaymentRefund(User user, Payment payment, Order order, OrderItem orderItem, Long amount, String reason, RefundStatus status, String failureCode, String failureMessage, LocalDateTime requestedAt, LocalDateTime completedAt) {
        this.user = user;
        this.payment = payment;
//        this.order = order;
        this.orderItem = orderItem;
        this.amount = amount;
        this.reason = reason;
        this.status = status;
        this.failureCode = failureCode;
        this.failureMessage = failureMessage;
        this.requestedAt = requestedAt;
        this.completedAt = completedAt;
    }

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
