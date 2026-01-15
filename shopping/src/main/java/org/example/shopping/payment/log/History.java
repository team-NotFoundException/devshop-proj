package org.example.shopping.payment.log;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.shopping.payment.Payment;
import org.example.shopping.payment.PaymentRefund;
import org.example.shopping.payment.paymentEnum.PaymentMethod;
import org.example.shopping.payment.paymentEnum.PaymentStatus;
import org.example.shopping.users.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@Entity
@Table(name = "history_tb")
@Getter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private String old_value;
    private String new_value;
    // ======================================
    private String username;

    private String email;
    // ======================================
    private Long productId;

    private String productCode;

    private String productName;

    private Long amount;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private Field field;
    // ======================================
    private String reason;
    // ======================================
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updateAt;

    @Builder
    public History(User user, Payment payment,
                   String old_value, String new_value,
                   String username, String email,
                   Long productId, String productCode, String productName,
                   Long amount, Integer quantity, PaymentMethod method,
                   Field field, String reason) {
        this.user = user;
        this.payment = payment;
        this.old_value = old_value;
        this.new_value = new_value;
        this.username = username;
        this.email = email;
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.amount = amount;
        this.quantity = quantity;
        this.method = method;
        this.field = field;
        this.reason = reason;
    }
}
