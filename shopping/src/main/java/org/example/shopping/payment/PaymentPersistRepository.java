package org.example.shopping.payment;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
public class PaymentPersistRepository {
    private final EntityManager entityManager;

    @Transactional
    public Payment save(Payment payment){
        entityManager.persist(payment);
        return payment;
    }

}
