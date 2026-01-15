package org.example.shopping.order;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.shopping.payment.Payment;
import org.example.shopping.users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_tb")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<Payment> payments = new ArrayList<>();

    private LocalDateTime createdAt;

    @Builder
    public Order(Long id, User user, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void addPayment(Payment payment) {
        this.getPayments().add(payment);
    }
}
